package ru.vantsyn.it.planet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.vantsyn.it.planet.model.exception.AuthorizeException;
import ru.vantsyn.it.planet.model.service.AccountService;

import java.io.IOException;

@Component
@Order(10)
public class CheckAuthorizeValidFilter implements Filter {
    private final AccountService accountService;

    public CheckAuthorizeValidFilter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String basicAuth = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (basicAuth != null) {
            if (!accountService.isAuthorizeValid(basicAuth)) {
                throw new AuthorizeException(HttpStatus.UNAUTHORIZED);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
