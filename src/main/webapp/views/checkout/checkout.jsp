<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Thanh toán đơn hàng - COD</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1">Thanh toán đơn hàng</h1>
            <p class="text-muted mb-0">Hình thức: Thanh toán khi nhận hàng (Cash On Delivery - COD)</p>
        </div>
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
            &larr; Quay lại giỏ hàng
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Lỗi:</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <div class="col-lg-7">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-primary text-white py-3">
                    <h5 class="card-title mb-0">1. Thông tin người nhận hàng</h5>
                </div>
                <div class="card-body p-4">
                    <form id="checkoutForm" method="post" action="${pageContext.request.contextPath}/checkout">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Họ và tên người nhận <span class="text-danger">*</span></label>
                            <input type="text" name="recipientName" class="form-control" value="${not empty param.recipientName ? param.recipientName : sessionScope.account.fullname}" required placeholder="Ví dụ: Nguyễn Văn A">
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Số điện thoại liên hệ <span class="text-danger">*</span></label>
                            <input type="tel" name="recipientPhone" class="form-control" value="${not empty param.recipientPhone ? param.recipientPhone : sessionScope.account.phone}" required placeholder="Ví dụ: 0912345678">
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Địa chỉ nhận hàng chi tiết <span class="text-danger">*</span></label>
                            <textarea name="recipientAddress" class="form-control" rows="3" required placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố...">${not empty param.recipientAddress ? param.recipientAddress : ''}</textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Ghi chú giao hàng (nếu có)</label>
                            <textarea name="note" class="form-control" rows="2" placeholder="Ví dụ: Giao hàng vào giờ hành chính, gọi trước khi giao...">${not empty param.note ? param.note : ''}</textarea>
                        </div>

                        <hr class="my-4">

                        <h5 class="fw-bold mb-3">2. Phương thức thanh toán</h5>
                        <div class="card border-primary bg-light-subtle mb-3">
                            <div class="card-body d-flex align-items-start">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="paymentMethod" id="paymentCOD" value="COD" checked>
                                    <label class="form-check-label fw-bold" for="paymentCOD">
                                        Thanh toán khi nhận hàng (COD)
                                    </label>
                                    <div class="text-muted small mt-1">
                                        Quý khách sẽ kiểm tra hàng và thanh toán tiền mặt trực tiếp cho nhân viên giao hàng khi nhận được sách.
                                    </div>
                                </div>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success btn-lg w-100 fw-bold py-3 mt-3 shadow-sm">
                            &#10004; Xác nhận đặt hàng COD
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-lg-5">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-dark text-white py-3">
                    <h5 class="card-title mb-0">Tóm tắt sản phẩm (${sessionScope.cart.totalQuantity} cuốn)</h5>
                </div>
                <div class="card-body p-3">
                    <div class="list-group list-group-flush mb-3">
                        <c:forEach items="${sessionScope.cart.items}" var="item">
                            <div class="list-group-item px-0 py-2 d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center" style="max-width: 70%;">
                                    <img src="${item.book.coverImage}" alt="Bìa" class="rounded me-2" style="width: 40px; height: 55px; object-fit: cover;">
                                    <div>
                                        <div class="fw-semibold text-truncate" style="max-width: 200px;">
                                            <c:out value="${item.book.title}"/>
                                        </div>
                                        <small class="text-muted">SL: ${item.quantity} &times; <fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,##0"/> đ</small>
                                    </div>
                                </div>
                                <div class="text-end fw-bold text-danger">
                                    <fmt:formatNumber value="${item.totalPrice}" type="number" pattern="#,##0"/> đ
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="border-top pt-3">
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Tạm tính:</span>
                            <span class="fw-semibold"><fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number" pattern="#,##0"/> đ</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Phí vận chuyển:</span>
                            <span class="text-success fw-semibold">Miễn phí</span>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <span class="text-muted">Hình thức thanh toán:</span>
                            <span class="badge bg-primary">COD</span>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="h5 mb-0">Tổng thanh toán:</span>
                            <span class="h4 text-danger fw-bold mb-0">
                                <fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number" pattern="#,##0"/> đ
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
