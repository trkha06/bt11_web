package vn.edu.hcmute.bookstore.dao;

import java.sql.SQLException;
import java.util.List;
import vn.edu.hcmute.bookstore.model.OrderDetail_24162054;
import vn.edu.hcmute.bookstore.model.Order_24162054;

public interface OrderDAO_24162054 {
    int createOrder(Order_24162054 order, List<OrderDetail_24162054> items) throws SQLException;
    Order_24162054 findById(int id);
    List<Order_24162054> findByUserId(int userId);
    List<Order_24162054> findByUserIdAndStatus(int userId, String status);
    List<OrderDetail_24162054> findDetailsByOrderId(int orderId);
}
