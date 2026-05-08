package com.globaladdresshub.address.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaladdresshub.address.exception.ApiErrorCode;
import com.globaladdresshub.address.exception.ApiException;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.CountryCode;
import com.globaladdresshub.address.model.CountryOption;
import com.globaladdresshub.address.model.RegionAddressData;
import com.globaladdresshub.address.model.RegionOption;
import com.globaladdresshub.address.rule.AddressRule;
import com.globaladdresshub.address.rule.AustraliaAddressRule;
import com.globaladdresshub.address.rule.CanadaAddressRule;
import com.globaladdresshub.address.rule.JapanAddressRule;
import com.globaladdresshub.address.rule.NigeriaAddressRule;
import com.globaladdresshub.address.rule.TurkeyAddressRule;
import com.globaladdresshub.address.rule.UkAddressRule;
import com.globaladdresshub.address.rule.UsAddressRule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class CountryRuleService {

    private final Map<String, CountryAddressData> countries;
    private final Map<String, AddressRule> rules;

    public CountryRuleService(ObjectMapper objectMapper) {
        this.countries = loadCountries(objectMapper);
        this.rules = Map.of(
                "US", new UsAddressRule(),
                "JP", new JapanAddressRule(),
                "UK", new UkAddressRule(),
                "CA", new CanadaAddressRule(),
                "AU", new AustraliaAddressRule(),
                "TR", new TurkeyAddressRule(),
                "NG", new NigeriaAddressRule()
        );
    }

    public List<CountryOption> countries() {
        return Arrays.stream(CountryCode.values())
                .map(code -> new CountryOption(code.name(), code.displayName()))
                .toList();
    }

    public List<RegionOption> regions(String country) {
        return countryData(country).regions().stream()
                .map(region -> new RegionOption(region.code(), region.name()))
                .toList();
    }

    public CountryAddressData countryData(String country) {
        CountryCode code = normalizeCountry(country);
        CountryAddressData data = countries.get(code.name());
        if (data == null) {
            throw new ApiException(ApiErrorCode.GENERATION_FAILED);
        }
        return data;
    }

    public AddressRule rule(String country) {
        CountryCode code = normalizeCountry(country);
        AddressRule rule = rules.get(code.name());
        if (rule == null) {
            throw new ApiException(ApiErrorCode.UNSUPPORTED_COUNTRY);
        }
        return rule;
    }

    public RegionAddressData region(CountryAddressData data, String region) {
        if (region == null || region.isBlank()) {
            List<RegionAddressData> regions = data.regions();
            return regions.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(regions.size()));
        }
        String normalized = normalizeRegion(region);
        Optional<RegionAddressData> selected = data.regions().stream()
                .filter(item -> item.code().equalsIgnoreCase(normalized))
                .findFirst();
        return selected.orElseThrow(() -> new ApiException(ApiErrorCode.UNSUPPORTED_REGION));
    }

    public CountryCode normalizeCountry(String country) {
        if (country == null || country.isBlank() || !country.trim().matches("[A-Za-z]{2}")) {
            throw new ApiException(ApiErrorCode.INVALID_COUNTRY);
        }
        return CountryCode.from(country).orElseThrow(() -> new ApiException(ApiErrorCode.UNSUPPORTED_COUNTRY));
    }

    public String normalizeRegion(String region) {
        if (region == null || region.isBlank() || !region.trim().matches("[A-Za-z0-9-]{1,10}")) {
            throw new ApiException(ApiErrorCode.INVALID_REGION);
        }
        return region.trim().toUpperCase(Locale.ROOT);
    }

    private Map<String, CountryAddressData> loadCountries(ObjectMapper objectMapper) {
        Map<String, CountryAddressData> loaded = new LinkedHashMap<>();
        for (CountryCode code : CountryCode.values()) {
            String fileName = "data/" + code.name().toLowerCase(Locale.ROOT) + "-address.json";
            try (InputStream inputStream = new ClassPathResource(fileName).getInputStream()) {
                CountryAddressData data = objectMapper.readValue(inputStream, CountryAddressData.class);
                validateCountryData(code, data, fileName);
                loaded.put(code.name(), data);
            } catch (IOException exception) {
                throw new ApiException(ApiErrorCode.GENERATION_FAILED, exception);
            }
        }
        return loaded;
    }

    private void validateCountryData(CountryCode expectedCode, CountryAddressData data, String source) {
        if (data == null) {
            throw invalidData(source, "data is empty");
        }
        if (!expectedCode.name().equals(data.countryCode())) {
            throw invalidData(source, "countryCode must be " + expectedCode.name());
        }
        requireText(data.country(), source, "country");
        requireList(data.firstNames(), source, "firstNames");
        requireList(data.lastNames(), source, "lastNames");
        requireList(data.streetNames(), source, "streetNames");
        requireList(data.regions(), source, "regions");
        for (RegionAddressData region : data.regions()) {
            if (region == null) {
                throw invalidData(source, "region must not be null");
            }
            requireText(region.code(), source, "region.code");
            requireText(region.name(), source, "region.name");
            requireList(region.phonePrefixes(), source, "region.phonePrefixes");
            requireList(region.cities(), source, "region.cities");
            for (var city : region.cities()) {
                if (city == null) {
                    throw invalidData(source, "city must not be null");
                }
                requireText(city.name(), source, "city.name");
                requireList(city.postalCodes(), source, "city.postalCodes");
            }
        }
    }

    private void requireText(String value, String source, String field) {
        if (value == null || value.isBlank()) {
            throw invalidData(source, field + " must not be blank");
        }
    }

    private void requireList(List<?> values, String source, String field) {
        if (values == null || values.isEmpty()) {
            throw invalidData(source, field + " must not be empty");
        }
        for (Object value : values) {
            if (value instanceof String text && text.isBlank()) {
                throw invalidData(source, field + " must not contain blank values");
            }
        }
    }

    private ApiException invalidData(String source, String reason) {
        String message = "Invalid address data in " + source + ": " + reason;
        return new ApiException(
                ApiErrorCode.GENERATION_FAILED,
                message,
                new IllegalStateException(message)
        );
    }
}
