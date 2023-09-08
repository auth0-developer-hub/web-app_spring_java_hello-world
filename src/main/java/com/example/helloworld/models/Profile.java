package com.example.helloworld.models;

import java.time.OffsetDateTime;

public record Profile(
        String nickname,
        String name,
        String picture,
        OffsetDateTime updatedAt,
        String email,
        Boolean emailVerified,
        String sub) {

    public static Profile of(
            final String nickname,
            final String name,
            final String picture,
            final OffsetDateTime updatedAt,
            final String email,
            final Boolean emailVerified,
            final String sub) {
        return new Profile(nickname, name, picture, updatedAt, email, emailVerified, sub);
    }
}
