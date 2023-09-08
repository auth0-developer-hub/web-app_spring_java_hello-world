package com.example.helloworld.controllers;

import static org.springframework.web.client.HttpClientErrorException.Forbidden;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.example.helloworld.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joselion.maybe.Maybe;

@Controller
public record MessageController(
        MessageService messageService,
        OAuth2AuthorizedClientService clientService,
        ObjectMapper mapper) {

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
    public String adminMessage(final Model model, final OAuth2AuthenticationToken authentication) {
        final var clientRegistrationId = authentication.getAuthorizedClientRegistrationId();
        final var principalName = authentication.getName();
        final var authorizedClient = clientService.loadAuthorizedClient(clientRegistrationId, principalName);
        final var accessToken = authorizedClient.getAccessToken();
        final var message = Maybe.just(accessToken)
                .resolve(messageService::getAdminMessage)
                .map(Object.class::cast)
                .catchError(Forbidden.class, this::getErrorBody)
                .orThrow();

        model.addAttribute(MESSAGE_KEY, message);

        return "admin";
    }

    private Object getErrorBody(final HttpClientErrorException error) {
        final var body = error.getResponseBodyAsByteArray();

        return Maybe
                .fromResolver(() -> mapper.readValue(body, Object.class))
                .orThrow(RuntimeException::new);
    }
}
