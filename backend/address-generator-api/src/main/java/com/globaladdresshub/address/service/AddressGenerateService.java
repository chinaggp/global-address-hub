package com.globaladdresshub.address.service;

import com.globaladdresshub.address.exception.ApiErrorCode;
import com.globaladdresshub.address.exception.ApiException;
import com.globaladdresshub.address.model.AddressResult;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.RegionAddressData;
import com.globaladdresshub.address.rule.AddressRule;
import org.springframework.stereotype.Service;

@Service
public class AddressGenerateService {

    private final CountryRuleService countryRuleService;
    private final CopyFormatService copyFormatService;

    public AddressGenerateService(CountryRuleService countryRuleService, CopyFormatService copyFormatService) {
        this.countryRuleService = countryRuleService;
        this.copyFormatService = copyFormatService;
    }

    public AddressResult generate(String country, String region) {
        try {
            CountryAddressData data = countryRuleService.countryData(country);
            RegionAddressData selectedRegion = countryRuleService.region(data, region);
            AddressRule rule = countryRuleService.rule(country);
            return rule.generate(data, selectedRegion, copyFormatService);
        } catch (ApiException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new ApiException(ApiErrorCode.GENERATION_FAILED, exception);
        }
    }
}
