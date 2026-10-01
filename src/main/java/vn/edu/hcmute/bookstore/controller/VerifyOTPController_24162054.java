package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet("/verify-otp") public class VerifyOTPController_24162054 extends HttpServlet {
    private final UserService_24162054 users=new UserServiceImpl_24162054();
    protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        r.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(r, p);
    }
    protected void doPost(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        Object o=r.getSession().getAttribute("pendingRegistration");
        if (!(o instanceof PendingRegistration_24162054 x)||x.getExpiresAt()<System.currentTimeMillis()||!x.getOtp().equals(ControllerUtil_24162054.clean(r.getParameter("otp")))){
            r.setAttribute("error", "OTP khong dung hoac da het han.");
            r.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(r, p);
            return;
        }
        users.register(x.getUser());
        r.getSession().removeAttribute("pendingRegistration");
        ControllerUtil_24162054.flash(r, "Kich hoat tai khoan thanh cong. Hay dang nhap.");
        p.sendRedirect(r.getContextPath()+"/login");
    }
 }
