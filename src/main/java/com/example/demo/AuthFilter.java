package com.example.demo;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.role.User;

import java.io.IOException;

@WebFilter("/api/products")
public class AuthFilter implements Filter {

private final Gson gson = new Gson();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if ("GET".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User currentUser = null;

        if (session != null) {
            currentUser = (User) session.getAttribute("currentUser");

        }

        if (currentUser == null){
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().print(gson.toJson("لطفاً ابتدا وارد شوید"));
            return;
        }

        chain.doFilter(request,response);



    }
}
