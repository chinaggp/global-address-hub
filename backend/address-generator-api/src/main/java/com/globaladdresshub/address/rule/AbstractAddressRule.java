package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.AddressResult;
import com.globaladdresshub.address.model.CityAddressData;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.RegionAddressData;
import com.globaladdresshub.address.service.CopyFormatService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

abstract class AbstractAddressRule implements AddressRule {

    @Override
    public AddressResult generate(CountryAddressData data, RegionAddressData region, CopyFormatService copyFormatService) {
        CityAddressData city = pick(region.cities());
        String fullName = pick(data.firstNames()) + " " + pick(data.lastNames());
        String street = street(data, city, region);
        String postalCode = postalCode(city);
        String phone = phone(region);
        AddressResult partial = new AddressResult(
                data.country(),
                data.countryCode(),
                fullName,
                street,
                city.name(),
                region.code(),
                region.name(),
                postalCode,
                phone,
                ""
        );
        return new AddressResult(
                partial.country(),
                partial.countryCode(),
                partial.fullName(),
                partial.street(),
                partial.city(),
                partial.regionCode(),
                partial.regionName(),
                partial.postalCode(),
                partial.phone(),
                copyFormatService.toFullAddress(partial)
        );
    }

    protected String street(CountryAddressData data, CityAddressData city, RegionAddressData region) {
        return number(10, 9999) + " " + pick(data.streetNames());
    }

    protected String postalCode(CityAddressData city) {
        return pick(city.postalCodes());
    }

    protected String phone(RegionAddressData region) {
        String prefix = pick(region.phonePrefixes());
        return prefix + " " + number(100, 999) + "-" + number(1000, 9999);
    }

    protected int number(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    protected <T> T pick(List<T> values) {
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }
}
