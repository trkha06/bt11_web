package vn.edu.hcmute.bookstore.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import vn.edu.hcmute.bookstore.dao.OrderDAO_24162054;
import vn.edu.hcmute.bookstore.dao.impl.OrderDAOImpl_24162054;
import vn.edu.hcmute.bookstore.model.CartItem_24162054;
import vn.edu.hcmute.bookstore.model.Cart_24162054;
import vn.edu.hcmute.bookstore.model.OrderDetail_24162054;
import vn.edu.hcmute.bookstore.model.Order_24162054;
import vn.edu.hcmute.bookstore.service.OrderService_24162054;

public class OrderServiceImpl_24162054 implements OrderService_24162054 {
    private final OrderDAO_24162054 dao = new OrderDAOImpl_24162054();

    @Override
    public int placeOrder(Order_24162054 order, Cart_24162054 cart) throws Exception {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng của bạn đang trống.");
        }

        order.setTotalAmount(cart.getTotalAmount());
        if (order.getPaymentMethod() == null || order.getPaymentMethod().isBlank()) {
            order.setPaymentMethod("COD");
        }
        order.setPaymentStatus("PENDING");
        if (order.getOrderStatus() == null || order.getOrderStatus().isBlank()) {
            order.setOrderStatus(Order_24162054.STATUS_NEW);
        }

        List<OrderDetail_24162054> details = new ArrayList<>();
        for (CartItem_24162054 item : cart.getItems()) {
            OrderDetail_24162054 d = new OrderDetail_24162054();
            d.setBookId(item.getBook().getBookId());
            d.setQuantity(item.getQuantity());
            d.setUnitPrice(item.getUnitPrice());
            d.setTotalPrice(item.getTotalPrice());
            details.add(d);
        }

        return dao.createOrder(order, details);
    }

    @Override
    public Order_24162054 getOrderById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<Order_24162054> getOrdersByUser(int userId) {
        return dao.findByUserId(userId);
    }

    @Override
    public List<Order_24162054> getOrdersByUserAndStatus(int userId, String status) {
        return dao.findByUserIdAndStatus(userId, status);
    }

    @Override
    public Map<String, Integer> getStatusCounts(int userId) {
        List<Order_24162054> all = dao.findByUserId(userId);
        Map<String, Integer> counts = new LinkedHashMap<>();
        counts.put("Tất cả", all.size());
        for (String st : Order_24162054.ALL_STATUSES) {
            counts.put(st, 0);
        }
        for (Order_24162054 o : all) {
            String displayStatus = o.getStatusDisplayName();
            if (counts.containsKey(displayStatus)) {
                counts.put(displayStatus, counts.get(displayStatus) + 1);
            }
        }
        return counts;
    }
}
