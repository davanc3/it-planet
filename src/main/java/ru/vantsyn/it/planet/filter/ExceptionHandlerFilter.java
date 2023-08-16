package ru.vantsyn.it.planet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vantsyn.it.planet.model.exception.AuthorizeException;

import java.io.IOException;

@Component
@Order(1)
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthorizeException ex) {
            response.setStatus(ex.getStatusCode().value());
            response.getWriter().write(ex.getMessage());
        }
    }
}
