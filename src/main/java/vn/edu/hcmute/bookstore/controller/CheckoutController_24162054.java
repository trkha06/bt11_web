package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vn.edu.hcmute.bookstore.model.Cart_24162054;
import vn.edu.hcmute.bookstore.model.Order_24162054;
import vn.edu.hcmute.bookstore.model.User_24162054;
import vn.edu.hcmute.bookstore.service.OrderService_24162054;
import vn.edu.hcmute.bookstore.service.impl.OrderServiceImpl_24162054;

@WebServlet("/checkout")
public class CheckoutController_24162054 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162054 orderService = new OrderServiceImpl_24162054();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User_24162054 user = (session != null) ? (User_24162054) session.getAttribute("account") : null;
        if (user == null) {
            ControllerUtil_24162054.flash(request, "Vui lòng đăng nhập để tiến hành thanh toán đơn hàng.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart_24162054 cart = (Cart_24162054) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            ControllerUtil_24162054.flash(request, "Giỏ hàng của bạn đang trống. Vui lòng chọn sản phẩm trước khi thanh toán.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.getRequestDispatcher("/views/checkout/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User_24162054 user = (session != null) ? (User_24162054) session.getAttribute("account") : null;
        if (user == null) {
            ControllerUtil_24162054.flash(request, "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart_24162054 cart = (Cart_24162054) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            ControllerUtil_24162054.flash(request, "Giỏ hàng của bạn đang trống.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        String recipientName = ControllerUtil_24162054.clean(request.getParameter("recipientName"));
        String recipientPhone = ControllerUtil_24162054.clean(request.getParameter("recipientPhone"));
        String recipientAddress = ControllerUtil_24162054.clean(request.getParameter("recipientAddress"));
        String note = ControllerUtil_24162054.clean(request.getParameter("note"));
        String paymentMethod = ControllerUtil_24162054.clean(request.getParameter("paymentMethod"));
        if (paymentMethod.isBlank()) {
            paymentMethod = "COD";
        }

        if (recipientName.isBlank() || recipientPhone.isBlank() || recipientAddress.isBlank()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ: Họ tên người nhận, Số điện thoại và Địa chỉ nhận hàng.");
            request.getRequestDispatcher("/views/checkout/checkout.jsp").forward(request, response);
            return;
        }

        Order_24162054 order = new Order_24162054();
        order.setUserId(user.getId());
        order.setRecipientName(recipientName);
        order.setRecipientPhone(recipientPhone);
        order.setRecipientAddress(recipientAddress);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);

        try {
            int orderId = orderService.placeOrder(order, cart);
            session.removeAttribute("cart");
            ControllerUtil_24162054.flash(request, "Đặt hàng COD thành công! Mã đơn hàng của bạn là #" + orderId);
            response.sendRedirect(request.getContextPath() + "/order-success?id=" + orderId);
        } catch (Exception e) {
            request.setAttribute("error", "Không thể hoàn tất đặt hàng: " + e.getMessage());
            request.getRequestDispatcher("/views/checkout/checkout.jsp").forward(request, response);
        }
    }
}
