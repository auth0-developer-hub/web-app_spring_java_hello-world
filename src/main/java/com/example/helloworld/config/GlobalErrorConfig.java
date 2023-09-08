package com.example.helloworld.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public record GlobalErrorConfig() {

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleInternalError(final HttpServletRequest request, final Exception ex) {
        final var modelAndView = new ModelAndView("error", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("title", "Server Error");
        modelAndView.addObject("description", ex.getMessage());

        return modelAndView;
    }
}
