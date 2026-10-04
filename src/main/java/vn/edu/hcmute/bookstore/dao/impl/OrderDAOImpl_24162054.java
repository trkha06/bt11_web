package vn.edu.hcmute.bookstore.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.edu.hcmute.bookstore.dao.OrderDAO_24162054;
import vn.edu.hcmute.bookstore.model.OrderDetail_24162054;
import vn.edu.hcmute.bookstore.model.Order_24162054;
import vn.edu.hcmute.bookstore.util.DBConnection_24162054;

public class OrderDAOImpl_24162054 implements OrderDAO_24162054 {

    static {
        ensureTables();
    }

    private static void ensureTables() {
        String createOrdersSql = "CREATE TABLE IF NOT EXISTS orders ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_id INT NOT NULL, "
                + "recipient_name VARCHAR(100) NOT NULL, "
                + "recipient_phone VARCHAR(20) NOT NULL, "
                + "recipient_address VARCHAR(255) NOT NULL, "
                + "note TEXT, "
                + "total_amount DECIMAL(10,2) NOT NULL, "
                + "payment_method VARCHAR(50) NOT NULL DEFAULT 'COD', "
                + "payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING', "
                + "order_status VARCHAR(50) NOT NULL DEFAULT 'Đơn hàng mới', "
                + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "CONSTRAINT fk_orders_user FOREIGN KEY(user_id) REFERENCES users(id)"
                + ")";

        String createOrderDetailsSql = "CREATE TABLE IF NOT EXISTS order_details ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "order_id INT NOT NULL, "
                + "book_id INT NOT NULL, "
                + "quantity INT NOT NULL, "
                + "unit_price DECIMAL(10,2) NOT NULL, "
                + "total_price DECIMAL(10,2) NOT NULL, "
                + "CONSTRAINT fk_od_order FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE, "
                + "CONSTRAINT fk_od_book FOREIGN KEY(book_id) REFERENCES books(bookid)"
                + ")";

        try (Connection c = DBConnection_24162054.getConnection();
             Statement s = c.createStatement()) {
            s.executeUpdate(createOrdersSql);
            s.executeUpdate(createOrderDetailsSql);
        } catch (SQLException e) {
            System.err.println("Warning ensuring order tables: " + e.getMessage());
        }
    }

    @Override
    public int createOrder(Order_24162054 order, List<OrderDetail_24162054> items) throws SQLException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng phải có ít nhất một sản phẩm.");
        }

        Connection c = null;
        try {
            c = DBConnection_24162054.getConnection();
            c.setAutoCommit(false);

            // 1. Verify stock and lock rows
            String checkStockSql = "SELECT quantity, title FROM books WHERE bookid = ? FOR UPDATE";
            String updateStockSql = "UPDATE books SET quantity = quantity - ? WHERE bookid = ?";

            try (PreparedStatement checkStmt = c.prepareStatement(checkStockSql);
                 PreparedStatement updateStmt = c.prepareStatement(updateStockSql)) {

                for (OrderDetail_24162054 item : items) {
                    checkStmt.setInt(1, item.getBookId());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Sản phẩm ID " + item.getBookId() + " không tồn tại.");
                        }
                        int currentStock = rs.getInt("quantity");
                        String title = rs.getString("title");
                        if (currentStock < item.getQuantity()) {
                            throw new SQLException("Sản phẩm \"" + title + "\" không đủ số lượng tồn kho (chỉ còn " + currentStock + " cuốn, bạn yêu cầu " + item.getQuantity() + " cuốn).");
                        }
                    }

                    // Deduct stock
                    updateStmt.setInt(1, item.getQuantity());
                    updateStmt.setInt(2, item.getBookId());
                    updateStmt.executeUpdate();
                }
            }

            // 2. Insert into orders table
            String insertOrderSql = "INSERT INTO orders(user_id, recipient_name, recipient_phone, recipient_address, note, total_amount, payment_method, payment_status, order_status, created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

            int orderId = 0;
            try (PreparedStatement orderStmt = c.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setString(2, order.getRecipientName());
                orderStmt.setString(3, order.getRecipientPhone());
                orderStmt.setString(4, order.getRecipientAddress());
                orderStmt.setString(5, order.getNote());
                orderStmt.setBigDecimal(6, order.getTotalAmount());
                orderStmt.setString(7, order.getPaymentMethod() != null ? order.getPaymentMethod() : "COD");
                orderStmt.setString(8, order.getPaymentStatus() != null ? order.getPaymentStatus() : "PENDING");
                orderStmt.setString(9, order.getOrderStatus() != null ? order.getOrderStatus() : Order_24162054.STATUS_NEW);

                orderStmt.executeUpdate();

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                        order.setId(orderId);
                    } else {
                        throw new SQLException("Không lấy được mã đơn hàng vừa tạo.");
                    }
                }
            }

            // 3. Insert order details
            String insertDetailSql = "INSERT INTO order_details(order_id, book_id, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement detailStmt = c.prepareStatement(insertDetailSql)) {
                for (OrderDetail_24162054 item : items) {
                    detailStmt.setInt(1, orderId);
                    detailStmt.setInt(2, item.getBookId());
                    detailStmt.setInt(3, item.getQuantity());
                    detailStmt.setBigDecimal(4, item.getUnitPrice());
                    detailStmt.setBigDecimal(5, item.getTotalPrice());
                    detailStmt.addBatch();
                }
                detailStmt.executeBatch();
            }

            c.commit();
            return orderId;
        } catch (SQLException e) {
            if (c != null) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback error: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (c != null) {
                try {
                    c.setAutoCommit(true);
                    c.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    @Override
    public Order_24162054 findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection c = DBConnection_24162054.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    Order_24162054 o = mapOrder(r);
                    o.setItems(findDetailsByOrderId(id));
                    return o;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    @Override
    public List<Order_24162054> findByUserId(int userId) {
        List<Order_24162054> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC";
        try (Connection c = DBConnection_24162054.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, userId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Order_24162054 o = mapOrder(r);
                    o.setItems(findDetailsByOrderId(o.getId()));
                    list.add(o);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return list;
    }

    @Override
    public List<Order_24162054> findByUserIdAndStatus(int userId, String status) {
        if (status == null || status.isBlank() || "all".equalsIgnoreCase(status) || "tất cả".equalsIgnoreCase(status) || "tat ca".equalsIgnoreCase(status)) {
            return findByUserId(userId);
        }

        String targetNormalized = Order_24162054.normalizeStatus(status);
        List<Order_24162054> all = findByUserId(userId);
        List<Order_24162054> filtered = new ArrayList<>();
        for (Order_24162054 o : all) {
            if (o.getStatusDisplayName().equalsIgnoreCase(targetNormalized) || o.getOrderStatus().equalsIgnoreCase(status.trim())) {
                filtered.add(o);
            }
        }
        return filtered;
    }

    @Override
    public List<OrderDetail_24162054> findDetailsByOrderId(int orderId) {
        List<OrderDetail_24162054> list = new ArrayList<>();
        String sql = "SELECT od.*, b.title, b.cover_image, b.publisher FROM order_details od "
                + "LEFT JOIN books b ON od.book_id = b.bookid WHERE od.order_id = ?";
        try (Connection c = DBConnection_24162054.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, orderId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    OrderDetail_24162054 d = new OrderDetail_24162054();
                    d.setId(r.getInt("id"));
                    d.setOrderId(r.getInt("order_id"));
                    d.setBookId(r.getInt("book_id"));
                    d.setQuantity(r.getInt("quantity"));
                    d.setUnitPrice(r.getBigDecimal("unit_price"));
                    d.setTotalPrice(r.getBigDecimal("total_price"));
                    d.setBookTitle(r.getString("title"));
                    d.setBookCoverImage(r.getString("cover_image"));
                    d.setBookPublisher(r.getString("publisher"));
                    list.add(d);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return list;
    }

    private Order_24162054 mapOrder(ResultSet r) throws SQLException {
        Order_24162054 o = new Order_24162054();
        o.setId(r.getInt("id"));
        o.setUserId(r.getInt("user_id"));
        o.setRecipientName(r.getString("recipient_name"));
        o.setRecipientPhone(r.getString("recipient_phone"));
        o.setRecipientAddress(r.getString("recipient_address"));
        o.setNote(r.getString("note"));
        o.setTotalAmount(r.getBigDecimal("total_amount"));
        o.setPaymentMethod(r.getString("payment_method"));
        o.setPaymentStatus(r.getString("payment_status"));
        o.setOrderStatus(r.getString("order_status"));
        Timestamp ts = r.getTimestamp("created_at");
        if (ts != null) {
            o.setCreatedAt(ts.toLocalDateTime());
        }
        return o;
    }
}
