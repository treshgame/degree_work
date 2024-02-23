package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/operations")
public class OperationsController {
    @GetMapping("")
    public String index(){
        return "operations";
    }
}
