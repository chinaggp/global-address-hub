package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class AustraliaAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "AU";
    }

    @Override
    protected String phone(RegionAddressData region) {
        return "+61 " + pick(region.phonePrefixes()) + " " + number(1000, 9999) + " " + number(1000, 9999);
    }
}
