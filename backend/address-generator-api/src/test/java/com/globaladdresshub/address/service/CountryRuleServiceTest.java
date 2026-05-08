package com.globaladdresshub.address.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaladdresshub.address.exception.ApiException;
import com.globaladdresshub.address.model.CityAddressData;
import com.globaladdresshub.address.model.CountryAddressData;
import com.globaladdresshub.address.model.CountryCode;
import com.globaladdresshub.address.model.RegionAddressData;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountryRuleServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void bundledCountryJsonFilesHaveCompleteStructure() throws Exception {
        for (CountryCode code : CountryCode.values()) {
            String fileName = "data/" + code.name().toLowerCase() + "-address.json";
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                assertThat(inputStream).as(fileName + " exists").isNotNull();
                CountryAddressData data = objectMapper.readValue(inputStream, CountryAddressData.class);

                assertThat(data.countryCode()).isEqualTo(code.name());
                assertThat(data.country()).isNotBlank();
                assertThat(data.firstNames()).isNotEmpty().allSatisfy(value -> assertThat(value).isNotBlank());
                assertThat(data.lastNames()).isNotEmpty().allSatisfy(value -> assertThat(value).isNotBlank());
                assertThat(data.streetNames()).isNotEmpty().allSatisfy(value -> assertThat(value).isNotBlank());
                assertThat(data.regions()).isNotEmpty();
                for (RegionAddressData region : data.regions()) {
                    assertThat(region.code()).isNotBlank();
                    assertThat(region.name()).isNotBlank();
                    assertThat(region.phonePrefixes()).isNotEmpty().allSatisfy(value -> assertThat(value).isNotBlank());
                    assertThat(region.cities()).isNotEmpty();
                    for (CityAddressData city : region.cities()) {
                        assertThat(city.name()).isNotBlank();
                        assertThat(city.postalCodes()).isNotEmpty().allSatisfy(value -> assertThat(value).isNotBlank());
                    }
                }
            }
        }
    }

    @Test
    void invalidLoadedCountryDataFailsFast() throws Exception {
        ObjectMapper mapper = mock(ObjectMapper.class);
        CountryAddressData invalid = new CountryAddressData(
                "United States",
                "US",
                List.of(new RegionAddressData("CA", "California", List.of(), List.of())),
                List.of("Alex"),
                List.of("Parker"),
                List.of("Maple Street")
        );
        when(mapper.readValue(any(InputStream.class), eq(CountryAddressData.class))).thenReturn(invalid);

        assertThatThrownBy(() -> new CountryRuleService(mapper))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Invalid address data");
    }
}
