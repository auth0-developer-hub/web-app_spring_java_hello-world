package com.example.helloworld.controllers;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.helloworld.models.Profile;

@Controller
public record ProfileController() {

    @GetMapping("/profile")
    public String profile(final Model model) {
        final var profile = Profile.of(
                "Customer",
                "One Customer",
                "https://cdn.auth0.com/blog/hello-auth0/auth0-user.png",
                OffsetDateTime.parse("2021-05-04T21:33:09.415Z"),
                "customer@example.com",
                false,
                "auth0|12345678901234567890");

        model.addAttribute("profile", profile);

        return "profile";
    }
}
