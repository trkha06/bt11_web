package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 import vn.edu.hcmute.bookstore.util.*;
 @WebServlet("/register") public class RegisterController_24162054 extends HttpServlet {
     private final UserService_24162054 users=new UserServiceImpl_24162054();
     protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        r.getRequestDispatcher("/views/auth/register.jsp").forward(r, p);
    }
     protected void doPost(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        String e=ControllerUtil_24162054.clean(r.getParameter("email")), name=ControllerUtil_24162054.clean(r.getParameter("fullname")), phone=ControllerUtil_24162054.clean(r.getParameter("phone")), pass=r.getParameter("password"), confirm=r.getParameter("confirmPassword");
        if (!e.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")||name.isBlank()||!phone.matches("\\d{9,10}")||pass==null||pass.length()<6||!pass.equals(confirm)||users.emailExists(e)){
            r.setAttribute("error", "Du lieu khong hop le, email da ton tai, hoac mat khau khong khop.");
            r.getRequestDispatcher("/views/auth/register.jsp").forward(r, p);
            return;
        }
        User_24162054 u=new User_24162054();
        u.setEmail(e);
        u.setFullname(name);
        u.setPhone(phone);
        u.setPasswd(pass);
        String otp=OTPUtil_24162054.generate();
        r.getSession().setAttribute("pendingRegistration", new PendingRegistration_24162054(u, otp, System.currentTimeMillis()+600000));
        boolean sent=EmailUtil_24162054.sendOtp(e, otp);
        r.setAttribute("notice", sent?"OTP da duoc gui den email.":"Chua cau hinh Email. Kiem tra log server de lay OTP khi phat trien.");
        r.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(r, p);
    }
 }
