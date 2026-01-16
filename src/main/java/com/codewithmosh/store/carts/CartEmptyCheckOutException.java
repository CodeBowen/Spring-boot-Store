package com.codewithmosh.store.carts;

public class CartEmptyCheckOutException extends RuntimeException {
    public CartEmptyCheckOutException() {
        super("Cart is empty");
    }
}
