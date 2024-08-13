package xyz.mariosm.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class IndexController {
    @GetMapping(path = "/")
    Map<String, String> get() {
        return Map.of("msg", "Hello World!");
    }
}
