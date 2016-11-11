package org.minigur.site.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
