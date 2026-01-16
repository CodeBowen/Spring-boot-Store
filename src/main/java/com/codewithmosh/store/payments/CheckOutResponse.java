package com.codewithmosh.store.payments;

import lombok.Data;

@Data
public class CheckOutResponse {
    private Long orderId;
    private String checkoutUrl;

    public CheckOutResponse(Long orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
