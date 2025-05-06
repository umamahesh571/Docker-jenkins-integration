package com.evolve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<h1>WELCOME TO EVOLVE TECHNOLOGIES</h1>" +
               "<p>THIS IS EXAMPLE FOR DOCKER INTEGRATION WITH JENKINS</p>" +
               "<p>IF YOU SEE THIS PAGE YOU SHOULD FEEL LIKE A CHAMP</p>";
    }
}
