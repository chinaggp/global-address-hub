package com.globaladdresshub.address.rule;

import com.globaladdresshub.address.model.AddressResult;
import com.globaladdresshub.address.service.AddressGenerateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressRuleTest {

    @Autowired
    private AddressGenerateService service;

    @Test
    void countrySpecificPostalAndPhoneFormatsAreApplied() {
        assertThat(service.generate("US", "CA").postalCode()).matches("\\d{5}");
        assertThat(service.generate("US", "CA").phone()).matches("\\(\\d{3}\\) \\d{3}-\\d{4}");

        assertThat(service.generate("JP", "13").postalCode()).matches("\\d{3}-\\d{4}");
        assertThat(service.generate("UK", "ENG").postalCode()).matches("[A-Z]{1,2}\\d[A-Z]? \\d[A-Z]{2}");
        assertThat(service.generate("CA", "ON").postalCode()).matches("[A-Z]\\d[A-Z] \\d[A-Z]\\d");
        assertThat(service.generate("AU", "NSW").postalCode()).matches("\\d{4}");

        AddressResult turkey = service.generate("TR", "34");
        assertThat(turkey.postalCode()).matches("\\d{5}");
        assertThat(turkey.phone()).startsWith("+90 ");

        AddressResult nigeria = service.generate("NG", "LA");
        assertThat(nigeria.postalCode()).matches("\\d{6}");
        assertThat(nigeria.phone()).startsWith("+234 ");
    }
}
