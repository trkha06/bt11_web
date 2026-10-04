package vn.edu.hcmute.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order_24162054 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String STATUS_NEW = "Đơn hàng mới";
    public static final String STATUS_CONFIRMED = "Đã xác nhận";
    public static final String STATUS_PREPARING = "Chuẩn bị hàng";
    public static final String STATUS_SHIPPING = "Vận chuyển";
    public static final String STATUS_DELIVERING = "Giao hàng";
    public static final String STATUS_DELIVERED = "Đã giao";
    public static final String STATUS_CANCELLED = "Đơn hàng hủy";
    public static final String STATUS_RETURNED = "Đơn hàng hoàn";

    public static final String[] ALL_STATUSES = {
        STATUS_NEW,
        STATUS_CONFIRMED,
        STATUS_PREPARING,
        STATUS_SHIPPING,
        STATUS_DELIVERING,
        STATUS_DELIVERED,
        STATUS_CANCELLED,
        STATUS_RETURNED
    };

    private int id;
    private int userId;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String note;
    private BigDecimal totalAmount;
    private String paymentMethod = "COD";
    private String paymentStatus = "PENDING";
    private String orderStatus = STATUS_NEW;
    private LocalDateTime createdAt;

    private List<OrderDetail_24162054> items = new ArrayList<>();

    public Order_24162054() {
    }

    public static String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return "";
        }
        String s = status.trim().toLowerCase();
        switch (s) {
            case "new":
            case "pending":
            case "don_hang_moi":
            case "don hang moi":
            case "đơn hàng mới":
                return STATUS_NEW;
            case "confirmed":
            case "da_xac_nhan":
            case "da xac nhan":
            case "đã xác nhận":
                return STATUS_CONFIRMED;
            case "preparing":
            case "processing":
            case "chuan_bi_hang":
            case "chuan bi hang":
            case "chuẩn bị hàng":
            case "đang chuẩn bị":
                return STATUS_PREPARING;
            case "shipping":
            case "van_chuyen":
            case "van chuyen":
            case "vận chuyển":
            case "đang vận chuyển":
                return STATUS_SHIPPING;
            case "delivering":
            case "giao_hang":
            case "giao hang":
            case "giao hàng":
            case "đang giao hàng":
                return STATUS_DELIVERING;
            case "delivered":
            case "completed":
            case "da_giao":
            case "da giao":
            case "đã giao":
            case "đã giao hàng":
                return STATUS_DELIVERED;
            case "cancelled":
            case "canceled":
            case "don_hang_huy":
            case "don hang huy":
            case "đơn hàng hủy":
            case "đã hủy":
                return STATUS_CANCELLED;
            case "returned":
            case "don_hang_hoan":
            case "don hang hoan":
            case "đơn hàng hoàn":
            case "hoàn hàng":
                return STATUS_RETURNED;
            default:
                return status.trim();
        }
    }

    public String getStatusDisplayName() {
        return normalizeStatus(this.orderStatus);
    }

    public String getStatusBadgeClass() {
        String name = getStatusDisplayName();
        switch (name) {
            case STATUS_NEW:
                return "bg-info text-dark";
            case STATUS_CONFIRMED:
                return "bg-primary";
            case STATUS_PREPARING:
                return "bg-warning text-dark";
            case STATUS_SHIPPING:
                return "bg-secondary";
            case STATUS_DELIVERING:
                return "bg-info";
            case STATUS_DELIVERED:
                return "bg-success";
            case STATUS_CANCELLED:
                return "bg-danger";
            case STATUS_RETURNED:
                return "bg-dark";
            default:
                return "bg-secondary";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderDetail_24162054> getItems() {
        return items;
    }

    public void setItems(List<OrderDetail_24162054> items) {
        this.items = items;
    }
}
