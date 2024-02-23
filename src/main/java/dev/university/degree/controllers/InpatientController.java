package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/inpatient")
public class InpatientController {
    @GetMapping("")
    public String index(){
        return "inpatient";
    }
}
