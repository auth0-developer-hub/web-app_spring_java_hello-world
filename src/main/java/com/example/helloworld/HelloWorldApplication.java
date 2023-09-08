package com.example.helloworld;

import static java.util.Arrays.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@ConfigurationPropertiesScan
public class HelloWorldApplication {

    enum DotEnv {
        PORT,
        OKTA_OAUTH2_ISSUER,
        OKTA_OAUTH2_CLIENT_ID,
        OKTA_OAUTH2_CLIENT_SECRET,
        OKTA_OAUTH2_AUDIENCE,
        API_SERVER_URL
    }

    public static void main(final String[] args) {
        dotEnvSafeCheck();

        SpringApplication.run(HelloWorldApplication.class, args);
    }

    private static void dotEnvSafeCheck() {
        final var dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        stream(DotEnv.values())
                .map(DotEnv::name)
                .filter(varName -> dotenv.get(varName, "").isEmpty())
                .findFirst()
                .ifPresent(varName -> {
                    log.error("[Fatal] Missing or empty environment variable: {}", varName);

                    System.exit(1);
                });
    }
}
