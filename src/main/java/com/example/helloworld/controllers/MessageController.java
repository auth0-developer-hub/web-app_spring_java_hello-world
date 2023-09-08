package com.example.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.helloworld.services.MessageService;

@Controller
public record MessageController(MessageService messageService) {

    private static final String MESSAGE_KEY = "message";

    @GetMapping("/public")
    public String publicMessage(final Model model) {
        final var message = messageService.getPublicMessage();
        model.addAttribute(MESSAGE_KEY, message);

        return "public";
    }

    @GetMapping("/protected")
    public String protectedMessage(final Model model) {
        final var message = messageService.getProtectedMessage();
        model.addAttribute(MESSAGE_KEY, message);

        return "protected";
    }

    @GetMapping("/admin")
    public String adminMessage(final Model model) {
        final var message = messageService.getAdminMessage();
        model.addAttribute(MESSAGE_KEY, message);

        return "admin";
    }
}
