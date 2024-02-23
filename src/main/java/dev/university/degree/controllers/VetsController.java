package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/vets")
public class VetsController {
    @GetMapping("")
    public String index(){
        return "vets";
    }
}
