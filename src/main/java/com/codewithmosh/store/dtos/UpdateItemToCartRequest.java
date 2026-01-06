package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateItemToCartRequest {
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0" )
    @Max(value = 100, message = "Quantity must be less than 100" )
    private Integer quantity;
}
