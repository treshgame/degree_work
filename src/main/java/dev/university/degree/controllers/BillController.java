package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/bills")
public class BillController {
    @GetMapping("")
    public String index(){

    }
}
