package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.RegionAddressData;

public class UkAddressRule extends AbstractAddressRule {

    @Override
    public String countryCode() {
        return "UK";
    }

    @Override
    protected String phone(RegionAddressData region) {
        return pick(region.phonePrefixes()) + " " + number(100, 999) + " " + number(1000, 9999);
    }
}
