package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionsController {
    @GetMapping("")
    public String index(){
        return "prescriptions";
    }
}
