package com.globaladdresshub.address.model;

import java.util.List;

public record CountryAddressData(
        String country,
        String countryCode,
        List<RegionAddressData> regions,
        List<String> firstNames,
        List<String> lastNames,
        List<String> streetNames
) {
}
