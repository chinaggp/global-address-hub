package com.globaladdresshub.address.controller;

import com.globaladdresshub.address.AddressGeneratorApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = AddressGeneratorApiApplication.class,
        properties = "app.rate-limit.random-address-per-minute=1"
)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RateLimitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void secondRandomAddressRequestReturnsRateLimitedJson() throws Exception {
        mockMvc.perform(get("/api/address/random").param("country", "US"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/address/random").param("country", "US"))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("RATE_LIMITED")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.requestId", notNullValue()));
    }
}
