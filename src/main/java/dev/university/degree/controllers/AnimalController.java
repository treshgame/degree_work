package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/animals")
public class AnimalController {
    @GetMapping("")
    public String index(){
        return "/animals";
    }

}
