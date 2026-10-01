package vn.edu.hcmute.bookstore.filter;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.User_24162054;
 @WebFilter("/admin/*") public class AdminFilter_24162054 implements Filter {
    public void doFilter(ServletRequest a, ServletResponse b, FilterChain c)throws IOException, ServletException{
        HttpServletRequest r=(HttpServletRequest)a;
        HttpServletResponse p=(HttpServletResponse)b;
        Object x=r.getSession(false)==null?null:r.getSession(false).getAttribute("account");
        if (!(x instanceof User_24162054 u)||!u.isAdmin()){
            p.sendError(HttpServletResponse.SC_FORBIDDEN, "Chi admin duoc truy cap.");
            return;
        }
        c.doFilter(a, b);
    }
}
