package com.globaladdresshub.address.service;

import com.globaladdresshub.address.model.AddressResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyFormatService {

    public String toFullAddress(AddressResult result) {
        return String.join("\n", toCopyLines(result));
    }

    public List<String> toCopyLines(AddressResult result) {
        return List.of(
                result.fullName(),
                result.street(),
                result.city() + ", " + result.regionName() + " " + result.postalCode(),
                result.country(),
                result.phone()
        );
    }
}
