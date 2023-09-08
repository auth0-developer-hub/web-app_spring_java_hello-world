package com.example.helloworld.models;

public record Message(String text) {

    public static Message of(final String text) {
        return new Message(text);
    }
}
