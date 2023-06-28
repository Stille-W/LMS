package com.reality.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Set;

@Configuration
@WebFilter(urlPatterns = "/goodsunlms/*")
@Order(value = 1)
public class loginFilter implements Filter {

    private static final Set<String> ALLOWED_PATH = Set.of("/index", "/adduser", "/login", "/logout", ".css", ".js", "/img");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("init loginFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowPath = ALLOWED_PATH.stream().anyMatch(p -> path.contains(p));

        if (!allowPath) {
            HttpSession session = req.getSession();
            boolean isLogin = session.getAttribute("userId")==null?false:true;

            if (!isLogin) {
                res.sendRedirect("/goodsunlms/login");
                return;
            }
        }

        response.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
//        System.out.println("destroy loginFilter");
    }
}
