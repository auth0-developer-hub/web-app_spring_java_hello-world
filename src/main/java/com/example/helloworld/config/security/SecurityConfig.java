package com.example.helloworld.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.joselion.maybe.Maybe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ClientRegistrationRepository clientRegistrationRepo;

    @Bean
    public SecurityFilterChain httpSecurity(final HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/profile", "/protected", "/admin")
                        .authenticated().anyRequest()
                        .permitAll())
                .oauth2Login(loginConfig -> loginConfig
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile", true))
                .logout(logoutConfig -> logoutConfig
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name()))
                        .deleteCookies("SESSION")
                        .addLogoutHandler(this::auth0Logout))
                .build();
    }

    private void auth0Logout(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication) {
        final var clientRegistration = clientRegistrationRepo.findByRegistrationId("okta");
        final var issuerUri = clientRegistration.getProviderDetails().getIssuerUri();
        final var clientId = clientRegistration.getClientId();
        final var returnTo = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/")
                .build()
                .toString();
        final var logoutUrl = UriComponentsBuilder.fromHttpUrl(issuerUri)
                .path("/v2/logout")
                .queryParam("client_id", clientId)
                .queryParam("returnTo", returnTo)
                .encode()
                .build()
                .toString();

        Maybe.just(logoutUrl)
                .runEffect(response::sendRedirect)
                .orThrow(RuntimeException::new);
    }
}
