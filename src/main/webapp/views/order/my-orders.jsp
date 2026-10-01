<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Đơn hàng của tôi - Book Store</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1">Lịch sử đơn hàng của tôi</h1>
            <p class="text-muted mb-0">Theo dõi trạng thái các đơn hàng bạn đã đặt tại Book Store.</p>
        </div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
            &larr; Tiếp tục mua sắm
        </a>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="card shadow-sm py-5 text-center">
                <div class="card-body">
                    <div class="display-4 text-muted mb-3">&#128230;</div>
                    <h3 class="h4">Bạn chưa có đơn hàng nào</h3>
                    <p class="text-muted">Hãy chọn cho mình những cuốn sách yêu thích và trải nghiệm mua sắm nhé!</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4 py-2 mt-2">
                        Mua sắm ngay
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th scope="col">Mã ĐH</th>
                                <th scope="col">Ngày đặt</th>
                                <th scope="col">Người nhận</th>
                                <th scope="col">Địa chỉ nhận hàng</th>
                                <th scope="col" class="text-end">Tổng tiền</th>
                                <th scope="col" class="text-center">Thanh toán</th>
                                <th scope="col" class="text-center">Trạng thái</th>
                                <th scope="col" class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="ord">
                                <tr>
                                    <td class="fw-bold text-primary">#${ord.id}</td>
                                    <td><small>${ord.createdAt}</small></td>
                                    <td>
                                        <div class="fw-semibold"><c:out value="${ord.recipientName}"/></div>
                                        <small class="text-muted"><c:out value="${ord.recipientPhone}"/></small>
                                    </td>
                                    <td>
                                        <small class="text-truncate d-inline-block" style="max-width: 220px;" title="<c:out value='${ord.recipientAddress}'/>">
                                            <c:out value="${ord.recipientAddress}"/>
                                        </small>
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${ord.totalAmount}" type="number" pattern="#,##0"/> đ
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-info text-dark">${ord.paymentMethod}</span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-warning text-dark">${ord.orderStatus}</span>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/order-detail?id=${ord.id}" class="btn btn-sm btn-outline-primary">
                                            Chi tiết
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>
