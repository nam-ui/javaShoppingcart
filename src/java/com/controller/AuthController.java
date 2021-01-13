/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entity.User;
import com.model.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author tomnyson
 */
public class AuthController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        1. get username -> request.getParameter('username');
        2. get password ->request.getParameter('username');
         */
        String action = request.getParameter("action");

        if (action != null) {
            System.out.print("action: " + action);
            switch (action) {
                case "dangnhap":
                    DangNhap(request, response);
                    break;
                case "dangky":
                    DangKy(request, response);
                    break;
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public void DangNhap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.print("DangNhap: ");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao dao = new UserDao();
        User user = new User(username, password);
        boolean isAuth = dao.isLogin(user);
        if (isAuth) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "tài khoản hoặc mật khẩu bị sai !!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    public void DangKy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.print("DangKy: ");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
       
        UserDao dao = new UserDao();
        User user = new User(username, password, "user");
        boolean isExist = dao.isExistUser(username);
        System.out.print("isExist:" + isExist);
        if (isExist) {
            request.setAttribute("message", "Tài khoản đã có");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        boolean isCreate = dao.createUser(user);

        System.out.print("isCreate:" + isCreate);
        if (isCreate) {
            System.out.print("success:" + isCreate);
//            HttpSession session = request.getSession();
//            session.setAttribute("username", username);
//            session.setAttribute("password", password);
//            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            System.out.print("failed:" + isCreate);
//            request.setAttribute("message", "tài khoản hoặc mật khẩu bị sai !!");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}