package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test Message")
public class MessageController {
    @RequestMapping("/hello")
    public Message sayHello() {
        return new Message("Hello World");
    }
}
