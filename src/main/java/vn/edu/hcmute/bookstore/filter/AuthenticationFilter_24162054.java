package vn.edu.hcmute.bookstore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;

@WebFilter({"/review", "/checkout", "/order-success", "/my-orders", "/order-detail"})
public class AuthenticationFilter_24162054 implements Filter {
    @Override
    public void doFilter(ServletRequest a, ServletResponse b, FilterChain c) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) a;
        HttpServletResponse p = (HttpServletResponse) b;
        if (r.getSession(false) == null || r.getSession(false).getAttribute("account") == null) {
            r.getSession().setAttribute("flash", "Vui lòng đăng nhập để tiếp tục.");
            p.sendRedirect(r.getContextPath() + "/login");
            return;
        }
        c.doFilter(a, b);
    }
}
