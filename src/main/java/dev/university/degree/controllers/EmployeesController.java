package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/employees")
public class EmployeesController {
    @GetMapping("")
    public String index(){
        return "employee";
    }

}
