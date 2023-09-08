package com.example.helloworld.services;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.helloworld.config.ApplicationProperties;
import com.example.helloworld.models.Message;

@Service
public record MessageService(ApplicationProperties applicationProps) {

    public Message getPublicMessage() {
        return Message.of("This is a public message.");
    }

    public Message getProtectedMessage() {
        return Message.of("This is a protected message.");
    }

    public Message getAdminMessage(final OAuth2AccessToken accessToken) {
        final var restTemplate = new RestTemplate();
        final var apiUrl = applicationProps.apiServerUrl().concat("/api/messages/admin");
        final var entity = RequestEntity.get(apiUrl)
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(accessToken.getTokenValue());
                })
                .build();

        return restTemplate
                .exchange(entity, Message.class)
                .getBody();
    }
}
