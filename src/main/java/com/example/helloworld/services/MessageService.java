package com.example.helloworld.services;

import org.springframework.stereotype.Service;

import com.example.helloworld.models.Message;

@Service
public record MessageService() {

    public Message getPublicMessage() {
        return Message.of("This is a public message.");
    }

    public Message getProtectedMessage() {
        return Message.of("This is a protected message.");
    }

    public Message getAdminMessage() {
        return Message.of("This is an admin message.");
    }
}
