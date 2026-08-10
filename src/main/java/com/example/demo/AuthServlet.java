package com.example.demo;

import com.google.gson.Gson;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.role.User;
import service.AuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "authServlet", value = "/api/login")
public class AuthServlet extends HttpServlet {
    private AuthService authService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.login(username,password);


        if (user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("نام کاربری یا رمز عبور اشتباه است");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("currentUser",user);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(gson.toJson(Map.of("username",user.getUsername(),"role",user.getRole())));

    }








}
