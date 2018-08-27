package com.tentative.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Shinobu
 * @since 2018/8/27
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public Object test() throws IOException {

        return null;
    }

}
