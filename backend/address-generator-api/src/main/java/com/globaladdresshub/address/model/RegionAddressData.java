package com.globaladdresshub.address.model;

import java.util.List;

public record RegionAddressData(
        String code,
        String name,
        List<String> phonePrefixes,
        List<CityAddressData> cities
) {
}
