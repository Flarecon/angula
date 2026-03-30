package com.example.angula.services.basic;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    /**
     *
     * @param price
     * @return price after applying 10% discount
     */
    public double applyDiscount(double price) {
        if (price <= 0 ) return 0.0;
        return price * 0.9;
    }
}
