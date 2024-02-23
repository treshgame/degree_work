package dev.university.degree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/journal")
public class JournalController {
    @GetMapping("")
    public String index(){
        return "journal";
    }
}
