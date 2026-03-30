package com.example.angula.basic;

import com.example.angula.services.basic.DiscountService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountServiceTest {

    // create instance of the class we want to test
    // we are not using dependency injection cause it'll need to start entire application
    // so to keep tests fast we create new objects
    private final DiscountService discountService = new DiscountService();

    @Test
    void shouldApplyTenPercentDiscount() {

        // 1. Arrange (set up the data)
        double originalPrice = 100.0;

        // 2. Act (call the method)
        double finalPrice = discountService.applyDiscount(originalPrice);

        // 3. Assert (check the result)
        // we expect 100 * 0.9 to be 90.0
        assertThat(finalPrice).isEqualTo(90.0);
    }

}
