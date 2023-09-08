package com.example.helloworld.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.helloworld.models.Feature;

@Controller
public record HomeController() {

    @GetMapping("/")
    public String index(final Model model) {
        final var features = List.of(
                Feature.of(
                        "Identity Providers",
                        """
                                Auth0 supports social providers such as Google, Facebook, and Twitter, \
                                along with Enterprise providers such as Microsoft Office 365, Google \
                                Apps, and Azure. You can also use any OAuth 2.0 Authorization Server.
                                """,
                        "https://auth0.com/docs/connections",
                        "https://cdn.auth0.com/blog/hello-auth0/identity-providers-logo.svg"),
                Feature.of(
                        "Multi-Factor Authentication",
                        """
                                You can require your users to provide more than one piece of \
                                identifying information when logging in. MFA delivers one-time codes to \
                                your users via SMS, voice, email, WebAuthn, and push notifications.
                                """,
                        "https://auth0.com/docs/multifactor-authentication",
                        "https://cdn.auth0.com/blog/hello-auth0/mfa-logo.svg"),
                Feature.of(
                        "Attack Protection",
                        """
                                Auth0 can detect attacks and stop malicious attempts to access your \
                                application such as blocking traffic from certain IPs and displaying \
                                CAPTCHA. Auth0 supports the principle of layered protection in security \
                                that uses a variety of signals to detect and mitigate attacks.
                                """,
                        "https://auth0.com/docs/attack-protection",
                        "https://cdn.auth0.com/blog/hello-auth0/advanced-protection-logo.svg"),
                Feature.of(
                        "Serverless Extensibility",
                        """
                                Actions are functions that allow you to customize the behavior of Auth0. \
                                Each action is bound to a specific triggering event on the Auth0 \
                                platform. Auth0 invokes the custom code of these Actions when the \
                                corresponding triggering event is produced at runtime.
                                """,
                        "https://auth0.com/docs/actions",
                        "https://cdn.auth0.com/blog/hello-auth0/private-cloud-logo.svg"));

        model.addAttribute("features", features);

        return "home";
    }
}
