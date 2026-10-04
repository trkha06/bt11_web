<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Chi tiết đơn hàng #${order.id} - Book Store</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1">Chi tiết đơn hàng #${order.id}</h1>
            <p class="text-muted mb-0">Ngày đặt: ${order.createdAt}</p>
        </div>
        <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline-secondary">
            &larr; Quay lại danh sách đơn hàng
        </a>
    </div>

    <div class="row g-4">
        <div class="col-lg-8">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3">
                    <h5 class="card-title mb-0 fw-bold">Danh sách sản phẩm</h5>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th scope="col" style="width: 50%;">Sản phẩm</th>
                                <th scope="col" class="text-center" style="width: 15%;">Số lượng</th>
                                <th scope="col" class="text-end" style="width: 15%;">Đơn giá</th>
                                <th scope="col" class="text-end" style="width: 20%;">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${order.items}" var="item">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <c:if test="${not empty item.bookCoverImage}">
                                                <img src="${item.bookCoverImage}" alt="Bìa" class="rounded me-3" style="width: 45px; height: 60px; object-fit: cover;">
                                            </c:if>
                                            <div>
                                                <a href="${pageContext.request.contextPath}/book-detail?id=${item.bookId}" class="text-decoration-none text-dark fw-semibold">
                                                    <c:out value="${item.bookTitle}"/>
                                                </a>
                                                <c:if test="${not empty item.bookPublisher}">
                                                    <small class="text-muted d-block">NXB: <c:out value="${item.bookPublisher}"/></small>
                                                </c:if>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="text-center fw-semibold">${item.quantity}</td>
                                    <td class="text-end text-primary">
                                        <fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,##0"/> đ
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${item.totalPrice}" type="number" pattern="#,##0"/> đ
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot class="table-light">
                            <tr>
                                <td colspan="3" class="text-end fw-bold">Tổng thanh toán:</td>
                                <td class="text-end fw-bold text-danger h5 mb-0">
                                    <fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,##0"/> đ
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-primary text-white py-3">
                    <h5 class="card-title mb-0">Thông tin nhận hàng</h5>
                </div>
                <div class="card-body">
                    <p class="mb-2"><strong>Người nhận:</strong> <c:out value="${order.recipientName}"/></p>
                    <p class="mb-2"><strong>Số điện thoại:</strong> <c:out value="${order.recipientPhone}"/></p>
                    <p class="mb-2"><strong>Địa chỉ:</strong> <c:out value="${order.recipientAddress}"/></p>
                    <c:if test="${not empty order.note}">
                        <p class="mb-2"><strong>Ghi chú:</strong> <c:out value="${order.note}"/></p>
                    </c:if>
                    <hr>
                    <p class="mb-2"><strong>Hình thức TT:</strong> <span class="badge bg-light text-dark border">${order.paymentMethod} (COD)</span></p>
                    <p class="mb-2"><strong>Thanh toán:</strong> <span class="badge bg-secondary">${order.paymentStatus}</span></p>
                    <p class="mb-0"><strong>Trạng thái:</strong> <span class="badge ${order.statusBadgeClass} px-2 py-1"><c:out value="${order.statusDisplayName}"/></span></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
