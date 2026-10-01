package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vn.edu.hcmute.bookstore.model.Order_24162054;
import vn.edu.hcmute.bookstore.model.User_24162054;
import vn.edu.hcmute.bookstore.service.OrderService_24162054;
import vn.edu.hcmute.bookstore.service.impl.OrderServiceImpl_24162054;

@WebServlet("/order-success")
public class OrderSuccessController_24162054 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162054 orderService = new OrderServiceImpl_24162054();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User_24162054 user = (session != null) ? (User_24162054) session.getAttribute("account") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int orderId = ControllerUtil_24162054.positive(request.getParameter("id"), 0);
        Order_24162054 order = orderService.getOrderById(orderId);

        if (order == null || (order.getUserId() != user.getId() && !user.isAdmin())) {
            ControllerUtil_24162054.flash(request, "Không tìm thấy thông tin đơn hàng.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.setAttribute("order", order);
        request.getRequestDispatcher("/views/checkout/success.jsp").forward(request, response);
    }
}
