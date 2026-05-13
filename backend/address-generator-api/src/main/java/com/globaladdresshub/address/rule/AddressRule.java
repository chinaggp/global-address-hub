package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.AddressResult;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.RegionAddressData;
import com.globaladdresshub.address.service.CopyFormatService;

public interface AddressRule {
    String countryCode();

    AddressResult generate(CountryAddressData data, RegionAddressData region, CopyFormatService copyFormatService);
}
