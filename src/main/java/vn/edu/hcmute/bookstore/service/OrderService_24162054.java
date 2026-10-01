package vn.edu.hcmute.bookstore.service;

import java.util.List;
import vn.edu.hcmute.bookstore.model.Cart_24162054;
import vn.edu.hcmute.bookstore.model.Order_24162054;

public interface OrderService_24162054 {
    int placeOrder(Order_24162054 order, Cart_24162054 cart) throws Exception;
    Order_24162054 getOrderById(int id);
    List<Order_24162054> getOrdersByUser(int userId);
}
