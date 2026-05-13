package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class UsAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "US";
    }

    @Override
    protected String phone(RegionAddressData region) {
        String areaCode = pick(region.phonePrefixes());
        return "(" + areaCode + ") " + number(200, 999) + "-" + number(1000, 9999);
    }
}
