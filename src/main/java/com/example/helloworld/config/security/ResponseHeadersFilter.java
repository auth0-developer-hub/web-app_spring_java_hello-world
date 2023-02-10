package com.example.helloworld.config.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public record ResponseHeadersFilter() implements Filter {

  @Override
  public void doFilter(
    final ServletRequest request,
    final ServletResponse response,
    final FilterChain chain
  ) throws IOException, ServletException {
    if (response instanceof final HttpServletResponse httpResponse) {
      httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate");
    }

    chain.doFilter(request, response);
  }
}
