package com.example.helloworld.controllers;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_ID;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REDIRECT_URI;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.RESPONSE_TYPE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.STATE;

import java.util.Base64;

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

@Controller
public record AuthenticationController(
        ClientRegistrationRepository clientRegistrationRepo) {

    @GetMapping("/signup")
    public String signUp() {
        final var keyGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder());
        final var clientRegistration = clientRegistrationRepo.findByRegistrationId("okta");
        final var authorizationUri = clientRegistration.getProviderDetails().getAuthorizationUri();
        final var baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toString();
        final var redirectUri = new UriTemplate(clientRegistration.getRedirectUri())
                .expand(baseUrl, "login", clientRegistration.getRegistrationId());
        final var signUpUrl = UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam(RESPONSE_TYPE, "code")
                .queryParam(CLIENT_ID, clientRegistration.getClientId())
                .queryParam(REDIRECT_URI, redirectUri)
                .queryParam(STATE, keyGenerator.generateKey())
                .queryParam("screen_hint", "signup")
                .encode()
                .build()
                .toString();

        return "redirect:%s".formatted(signUpUrl);
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView("/oauth2/authorization/okta");
    }
}
