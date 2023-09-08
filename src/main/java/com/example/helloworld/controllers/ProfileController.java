package com.example.helloworld.controllers;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.helloworld.models.Profile;

@Controller
public record ProfileController() {

    @GetMapping("/profile")
    public String profile(final Model model, final @AuthenticationPrincipal OidcUser principal) {
        final var profile = Profile.of(
                principal.getNickName(),
                principal.getFullName(),
                principal.getPicture(),
                OffsetDateTime.ofInstant(principal.getUpdatedAt(), ZoneId.systemDefault()),
                principal.getEmail(),
                principal.getEmailVerified(),
                principal.getSubject());

        model.addAttribute("profile", profile);

        return "profile";
    }
}
