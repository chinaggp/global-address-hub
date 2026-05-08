package com.globaladdresshub.address.controller;

import com.globaladdresshub.address.model.AddressResult;
import com.globaladdresshub.address.model.CountryOption;
import com.globaladdresshub.address.model.RegionOption;
import com.globaladdresshub.address.service.AddressGenerateService;
import com.globaladdresshub.address.service.CountryRuleService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;


@RestController
public class AddressController {

    private static final CacheControl PUBLIC_CACHE = CacheControl.maxAge(Duration.ofHours(1)).cachePublic();
    private static final CacheControl NO_STORE = CacheControl.noStore();

    private final CountryRuleService countryRuleService;
    private final AddressGenerateService addressGenerateService;

    public AddressController(CountryRuleService countryRuleService, AddressGenerateService addressGenerateService) {
        this.countryRuleService = countryRuleService;
        this.addressGenerateService = addressGenerateService;
    }

    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryOption>> countries() {
        return ResponseEntity.ok().cacheControl(PUBLIC_CACHE).body(countryRuleService.countries());
    }

    @GetMapping("/api/regions")
    public ResponseEntity<List<RegionOption>> regions(@RequestParam String country) {
        return ResponseEntity.ok().cacheControl(PUBLIC_CACHE).body(countryRuleService.regions(country));
    }

    @GetMapping("/api/address/random")
    public ResponseEntity<AddressResult> randomAddress(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String state
    ) {
        String requestedRegion = region == null || region.isBlank() ? state : region;
        return ResponseEntity.ok()
                .cacheControl(NO_STORE)
                .body(addressGenerateService.generate(country, requestedRegion));
    }
}
