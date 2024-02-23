package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/owners")
public class OwnersController {
    @GetMapping("")
    public String index(){
        return "owners";
    }

}
