package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import vn.edu.hcmute.bookstore.model.Order_24162054;
import vn.edu.hcmute.bookstore.model.User_24162054;
import vn.edu.hcmute.bookstore.service.OrderService_24162054;
import vn.edu.hcmute.bookstore.service.impl.OrderServiceImpl_24162054;

@WebServlet({"/my-orders", "/order-detail"})
public class MyOrdersController_24162054 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162054 orderService = new OrderServiceImpl_24162054();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User_24162054 user = (session != null) ? (User_24162054) session.getAttribute("account") : null;
        if (user == null) {
            ControllerUtil_24162054.flash(request, "Vui lòng đăng nhập để xem lịch sử đơn hàng.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        if ("/order-detail".equals(path)) {
            int orderId = ControllerUtil_24162054.positive(request.getParameter("id"), 0);
            Order_24162054 order = orderService.getOrderById(orderId);
            if (order == null || (order.getUserId() != user.getId() && !user.isAdmin())) {
                ControllerUtil_24162054.flash(request, "Không tìm thấy đơn hàng yêu cầu.");
                response.sendRedirect(request.getContextPath() + "/my-orders");
                return;
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("/views/order/detail.jsp").forward(request, response);
            return;
        }

        // List user's orders
        List<Order_24162054> orders = orderService.getOrdersByUser(user.getId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/order/my-orders.jsp").forward(request, response);
    }
}
