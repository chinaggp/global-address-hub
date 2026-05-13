package com.globaladdresshub.address.controller;

import com.globaladdresshub.address.AddressGeneratorApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AddressGeneratorApiApplication.class)
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void countriesReturnsSevenOptionsWithCacheHeader() throws Exception {
        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "max-age=3600, public"))
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].code", is("US")));
    }

    @Test
    void regionsReturnsUnitedStatesRegionsWithCacheHeader() throws Exception {
        mockMvc.perform(get("/api/regions").param("country", "US"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "max-age=3600, public"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[?(@.code == 'CA')]", hasSize(1)));
    }

    @Test
    void randomAddressReturnsNoStoreAddressResult() throws Exception {
        mockMvc.perform(get("/api/address/random").param("country", "US"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "no-store"))
                .andExpect(jsonPath("$.country", is("United States")))
                .andExpect(jsonPath("$.countryCode", is("US")))
                .andExpect(jsonPath("$.fullName", notNullValue()))
                .andExpect(jsonPath("$.street", notNullValue()))
                .andExpect(jsonPath("$.city", notNullValue()))
                .andExpect(jsonPath("$.regionCode", notNullValue()))
                .andExpect(jsonPath("$.regionName", notNullValue()))
                .andExpect(jsonPath("$.postalCode", notNullValue()))
                .andExpect(jsonPath("$.phone", notNullValue()))
                .andExpect(jsonPath("$.fullAddress", notNullValue()));
    }

    @Test
    void randomAddressAcceptsStateAliasForRegion() throws Exception {
        mockMvc.perform(get("/api/address/random").param("country", "US").param("state", "CA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.regionCode", is("CA")));
    }

    @Test
    void emptyCountryReturnsInvalidCountryJson() throws Exception {
        mockMvc.perform(get("/api/address/random"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("INVALID_COUNTRY")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.requestId", notNullValue()));
    }

    @Test
    void unsupportedCountryReturnsUnsupportedCountryJson() throws Exception {
        mockMvc.perform(get("/api/address/random").param("country", "XX"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("UNSUPPORTED_COUNTRY")))
                .andExpect(jsonPath("$.requestId", notNullValue()));
    }

    @Test
    void unsupportedRegionReturnsUnsupportedRegionJson() throws Exception {
        mockMvc.perform(get("/api/address/random").param("country", "US").param("region", "ZZ"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("UNSUPPORTED_REGION")))
                .andExpect(jsonPath("$.requestId", notNullValue()));
    }

    @Test
    void unknownPathReturnsJsonEvenWhenHtmlIsAccepted() throws Exception {
        mockMvc.perform(get("/api/missing").accept(TEXT_HTML))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.requestId", notNullValue()));
    }
}
