package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class CanadaAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "CA";
    }

    @Override
    protected String phone(RegionAddressData region) {
        String areaCode = pick(region.phonePrefixes());
        return "+1 (" + areaCode + ") " + number(200, 999) + "-" + number(1000, 9999);
    }
}
