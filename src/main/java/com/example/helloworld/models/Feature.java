package com.example.helloworld.models;

public record Feature(
        String title,
        String description,
        String url,
        String imageSrc) {

    public static Feature of(
            final String title,
            final String description,
            final String url,
            final String imageSrc) {
        return new Feature(title, description, url, imageSrc);
    }
}
