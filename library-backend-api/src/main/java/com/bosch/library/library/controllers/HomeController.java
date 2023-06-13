package com.bosch.library.library.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HomeController {
    @GetMapping("/")
    public String getPage() {
        return "Program started";
    }
}
