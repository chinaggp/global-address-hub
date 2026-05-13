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

    private static final Map<String, String> ZH_COUNTRY_NAMES = Map.of(
            "US", "美国",
            "JP", "日本",
            "UK", "英国",
            "CA", "加拿大",
            "AU", "澳大利亚",
            "TR", "土耳其",
            "NG", "尼日利亚"
    );

    private static final Map<String, Map<String, String>> ZH_REGION_NAMES = Map.of(
            "US", Map.of(
                    "CA", "加利福尼亚州",
                    "NY", "纽约州",
                    "TX", "得克萨斯州",
                    "FL", "佛罗里达州",
                    "IL", "伊利诺伊州"
            ),
            "JP", Map.of(
                    "13", "东京都",
                    "27", "大阪府",
                    "14", "神奈川县",
                    "23", "爱知县",
                    "40", "福冈县"
            ),
            "UK", Map.of(
                    "ENG", "英格兰",
                    "SCT", "苏格兰",
                    "WLS", "威尔士",
                    "NIR", "北爱尔兰",
                    "CNL", "康沃尔"
            ),
            "CA", Map.of(
                    "ON", "安大略省",
                    "QC", "魁北克省",
                    "BC", "不列颠哥伦比亚省",
                    "AB", "艾伯塔省",
                    "NS", "新斯科舍省"
            ),
            "AU", Map.of(
                    "NSW", "新南威尔士州",
                    "VIC", "维多利亚州",
                    "QLD", "昆士兰州",
                    "WA", "西澳大利亚州",
                    "SA", "南澳大利亚州"
            ),
            "TR", Map.of(
                    "34", "伊斯坦布尔",
                    "06", "安卡拉",
                    "35", "伊兹密尔",
                    "16", "布尔萨",
                    "07", "安塔利亚"
            ),
            "NG", Map.of(
                    "LA", "拉各斯",
                    "FC", "联邦首都区",
                    "KN", "卡诺州",
                    "RV", "河流州",
                    "OY", "奥约州"
            )
    );

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
        return countries(null, null);
    }

    public List<CountryOption> countries(String locale, String lang) {
        boolean chinese = isChineseLocale(locale, lang);
        return Arrays.stream(CountryCode.values())
                .map(code -> new CountryOption(code.name(), countryName(code, chinese)))
                .toList();
    }

    public List<RegionOption> regions(String country) {
        return regions(country, null, null);
    }

    public List<RegionOption> regions(String country, String locale, String lang) {
        CountryCode countryCode = normalizeCountry(country);
        boolean chinese = isChineseLocale(locale, lang);
        return countryData(country).regions().stream()
                .map(region -> new RegionOption(region.code(), regionName(countryCode, region, chinese)))
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

    private String countryName(CountryCode code, boolean chinese) {
        if (!chinese) {
            return code.displayName();
        }
        return ZH_COUNTRY_NAMES.getOrDefault(code.name(), code.displayName());
    }

    private String regionName(CountryCode countryCode, RegionAddressData region, boolean chinese) {
        if (!chinese) {
            return region.name();
        }
        return ZH_REGION_NAMES.getOrDefault(countryCode.name(), Map.of())
                .getOrDefault(region.code(), region.name());
    }

    private boolean isChineseLocale(String locale, String lang) {
        String requested = firstText(locale, lang);
        if (requested == null) {
            return false;
        }
        String normalized = requested.trim().replace('_', '-').toLowerCase(Locale.ROOT);
        return "zh".equals(normalized) || normalized.startsWith("zh-");
    }

    private String firstText(String first, String second) {
        if (first != null && !first.isBlank()) {
            return first;
        }
        if (second != null && !second.isBlank()) {
            return second;
        }
        return null;
    }
}
