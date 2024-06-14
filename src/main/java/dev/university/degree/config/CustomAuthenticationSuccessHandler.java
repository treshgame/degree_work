package dev.university.degree.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("OWNER")) {
            response.sendRedirect("/owner");
        } else if (roles.contains("VET")) {
            response.sendRedirect("/vet");
        } else if (roles.contains("INPATIENT")) {
            response.sendRedirect("/inpatient");
        } else if (roles.contains("ADMINISTRATOR")) {
            response.sendRedirect("/administrator");
        } else {
            response.sendRedirect("/");
        }
    }
}
