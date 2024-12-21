package com.vulnapp.vulnerabilities.ldapinjection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LdapInjectionController {

    @GetMapping("/vulnerable/ldapinjection")
    public String showLdapInjectionPage() {
        return "ldapinjection/index"; // templates/ldapinjection/index.html
    }
}
