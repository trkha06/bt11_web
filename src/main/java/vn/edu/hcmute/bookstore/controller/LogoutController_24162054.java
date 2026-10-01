package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 @WebServlet("/logout") public class LogoutController_24162054 extends HttpServlet {
     protected void doGet(HttpServletRequest r, HttpServletResponse p)throws IOException{
        r.getSession().invalidate();
        p.sendRedirect(r.getContextPath()+"/login");
    }
 }
