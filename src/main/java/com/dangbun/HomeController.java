package com.dangbun;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HomeController {
    @GetMapping("/")
    public String func(){
        return "hello world!!!!!!!";
    }

}
