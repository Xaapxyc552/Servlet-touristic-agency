package ua.skidchenko.touristic_agency.controller.filters;



import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


public class LocalizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        //empty
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String language = servletRequest.getParameter("language");
        if (language != null && !language.isEmpty()) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            Locale localeToSet = Locale.forLanguageTag(language);
            resp.addCookie(new Cookie("language",localeToSet.toLanguageTag()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //empty
    }
}
