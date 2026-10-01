package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet("/login") public class LoginController_24162054 extends HttpServlet {
     private final UserService_24162054 users=new UserServiceImpl_24162054();
     protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        r.getRequestDispatcher("/views/auth/login.jsp").forward(r, p);
    }
     protected void doPost(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        String e=ControllerUtil_24162054.clean(r.getParameter("email")), pass=r.getParameter("password");
        User_24162054 u=users.login(e, pass==null?"":pass);
        if (u==null){
            r.setAttribute("error", "Email hoac mat khau khong dung.");
            r.getRequestDispatcher("/views/auth/login.jsp").forward(r, p);
            return;
        }
        r.getSession().setAttribute("account", u);
        ControllerUtil_24162054.flash(r, "Dang nhap thanh cong.");
        p.sendRedirect(r.getContextPath()+"/home");
    }
 }
