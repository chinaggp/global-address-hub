package com.globaladdresshub.address.model;

public record AddressResult(
        String country,
        String countryCode,
        String fullName,
        String street,
        String city,
        String regionCode,
        String regionName,
        String postalCode,
        String phone,
        String fullAddress
) {
}
