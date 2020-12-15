package ua.skidchenko.touristic_agency.controller.filters;



import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


public class LocalizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String language = servletRequest.getParameter("language");
        if (language != null && !language.isEmpty()) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            Locale localeToSet = new Locale(language);
            resp.addCookie(new Cookie("language",localeToSet.toString()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
