package ua.skidchenko.touristic_agency.controller.filters;


import ua.skidchenko.touristic_agency.entity.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String pathUrl = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        String userRoleAsString = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("role");
        if (userRoleAsString == null || userRoleAsString.isEmpty()) {
            ((HttpServletResponse) servletResponse).sendRedirect("/app/login");
            return;
        }
        Role userRole = Role.valueOf(userRoleAsString);
        if (pathUrl.contains("admin") && userRole.getAuthority().contains("ROLE_ADMIN")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (pathUrl.contains("manager") &&
                (userRole.getAuthority().equals("ROLE_MANAGER") || userRole.getAuthority().equals("ROLE_ADMIN"))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (pathUrl.contains("user") && userRole.getAuthority().equals("ROLE_USER")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/app/login");
        }
    }

    @Override
    public void destroy() {
    }
}
