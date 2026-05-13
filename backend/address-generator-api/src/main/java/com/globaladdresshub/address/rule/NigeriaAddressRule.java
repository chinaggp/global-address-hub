package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class NigeriaAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "NG";
    }

    @Override
    protected String phone(RegionAddressData region) {
        return "+234 " + pick(region.phonePrefixes()) + " " + number(100, 999) + " " + number(1000, 9999);
    }
}
