package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fenqing
 */
@RestController
@RequestMapping("/hello_world")
public class HelloWorldController {

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

}
