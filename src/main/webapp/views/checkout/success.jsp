<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Đặt hàng thành công - Book Store</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow-sm border-0">
                <div class="card-body p-4 p-md-5 text-center">
                    <div class="text-success mb-3" style="font-size: 4rem;">&#10004;</div>
                    <h1 class="h3 fw-bold text-success mb-2">Đặt hàng thành công!</h1>
                    <p class="text-muted mb-4">Cảm ơn bạn đã tin tưởng và mua sắm tại Book Store. Mã đơn hàng của bạn là: <strong class="text-dark">#${order.id}</strong></p>

                    <div class="card bg-light border-0 text-start p-4 mb-4">
                        <h5 class="fw-bold mb-3 border-bottom pb-2">Thông tin đơn hàng #${order.id}</h5>
                        <div class="row g-2 mb-3">
                            <div class="col-sm-4 text-muted">Người nhận:</div>
                            <div class="col-sm-8 fw-semibold"><c:out value="${order.recipientName}"/></div>

                            <div class="col-sm-4 text-muted">Số điện thoại:</div>
                            <div class="col-sm-8 fw-semibold"><c:out value="${order.recipientPhone}"/></div>

                            <div class="col-sm-4 text-muted">Địa chỉ nhận hàng:</div>
                            <div class="col-sm-8"><c:out value="${order.recipientAddress}"/></div>

                            <c:if test="${not empty order.note}">
                                <div class="col-sm-4 text-muted">Ghi chú:</div>
                                <div class="col-sm-8"><c:out value="${order.note}"/></div>
                            </c:if>

                            <div class="col-sm-4 text-muted">Phương thức thanh toán:</div>
                            <div class="col-sm-8"><span class="badge bg-primary">COD (Thanh toán khi nhận hàng)</span></div>

                            <div class="col-sm-4 text-muted">Trạng thái đơn hàng:</div>
                            <div class="col-sm-8"><span class="badge bg-warning text-dark">Đang xử lý</span></div>

                            <div class="col-sm-4 text-muted">Thời gian đặt:</div>
                            <div class="col-sm-8">${order.createdAt}</div>
                        </div>

                        <h6 class="fw-bold mb-2">Danh sách sản phẩm đã đặt:</h6>
                        <div class="table-responsive">
                            <table class="table table-sm table-bordered bg-white mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Sản phẩm</th>
                                        <th class="text-center">Số lượng</th>
                                        <th class="text-end">Đơn giá</th>
                                        <th class="text-end">Thành tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${order.items}" var="item">
                                        <tr>
                                            <td><c:out value="${item.bookTitle}"/></td>
                                            <td class="text-center">${item.quantity}</td>
                                            <td class="text-end"><fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,##0"/> đ</td>
                                            <td class="text-end fw-bold text-danger"><fmt:formatNumber value="${item.totalPrice}" type="number" pattern="#,##0"/> đ</td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="table-light">
                                        <td colspan="3" class="text-end fw-bold">Tổng thanh toán COD:</td>
                                        <td class="text-end fw-bold text-danger h6 mb-0">
                                            <fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,##0"/> đ
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="d-flex justify-content-center gap-3">
                        <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline-primary px-4">
                            Xem lịch sử đơn hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4">
                            Tiếp tục mua sách
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
