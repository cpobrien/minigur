package org.minigur.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteRenderController {
    @GetMapping("/")
    String home() {
        return "home";
    }
}
