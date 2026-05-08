package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.CityAddressData;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.RegionAddressData;

public class JapanAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "JP";
    }

    @Override
    protected String street(CountryAddressData data, CityAddressData city, RegionAddressData region) {
        return city.name() + " " + number(1, 9) + "-" + number(1, 25) + "-" + number(1, 40);
    }

    @Override
    protected String phone(RegionAddressData region) {
        String prefix = pick(region.phonePrefixes());
        return prefix + "-" + number(1000, 9999) + "-" + number(1000, 9999);
    }
}
