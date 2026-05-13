package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class TurkeyAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "TR";
    }

    @Override
    protected String phone(RegionAddressData region) {
        return "+90 " + pick(region.phonePrefixes()) + " " + number(100, 999) + " " + number(1000, 9999);
    }
}
