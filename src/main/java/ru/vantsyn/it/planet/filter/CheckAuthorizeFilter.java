package ru.vantsyn.it.planet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import ru.vantsyn.it.planet.model.exception.AuthorizeException;

import java.io.IOException;

public class CheckAuthorizeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String basicAuth = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (basicAuth != null) {
            throw new AuthorizeException(HttpStatus.FORBIDDEN);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
