package com.globaladdresshub.address.service;

import com.globaladdresshub.address.model.AddressResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressGenerateServiceTest {

    @Autowired
    private AddressGenerateService service;

    @Test
    void generatesAddressForAllSupportedCountries() {
        for (String country : Set.of("US", "JP", "UK", "CA", "AU", "TR", "NG")) {
            AddressResult result = service.generate(country, null);

            assertThat(result.countryCode()).isEqualTo(country);
            assertThat(result.fullName()).isNotBlank();
            assertThat(result.street()).isNotBlank();
            assertThat(result.city()).isNotBlank();
            assertThat(result.regionCode()).isNotBlank();
            assertThat(result.regionName()).isNotBlank();
            assertThat(result.postalCode()).isNotBlank();
            assertThat(result.phone()).isNotBlank();
            assertThat(result.fullAddress()).contains(result.fullName());
        }
    }

    @Test
    void generatesAddressForRequestedRegion() {
        AddressResult result = service.generate("us", "CA");

        assertThat(result.countryCode()).isEqualTo("US");
        assertThat(result.regionCode()).isEqualTo("CA");
        assertThat(result.regionName()).isEqualTo("California");
    }
}
