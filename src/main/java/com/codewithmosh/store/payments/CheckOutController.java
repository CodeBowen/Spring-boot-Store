package com.codewithmosh.store.payments;

import com.codewithmosh.store.common.ErrorDto;
import com.codewithmosh.store.carts.CartEmptyCheckOutException;
import com.codewithmosh.store.carts.CartNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckOutController {
    private final CheckOutService checkOutService;

    @PostMapping
    public CheckOutResponse checkOut(@Valid @RequestBody CheckOutRequest request) {
        return checkOutService.checkout(request.getCartId());

    }

    @PostMapping("/webhook")
    public void handleWebHook(
            @RequestHeader Map<String,String> headers,
            @RequestBody String payload
    ) {
        checkOutService.handleWebhookEvent(new WebhookRequest(headers,payload));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyCheckOutException.class})
    public ResponseEntity<ErrorDto> handleCartException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }
}
