<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Đơn hàng của tôi - Book Store</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <h1 class="h3 mb-1">Lịch sử đơn hàng của tôi</h1>
            <p class="text-muted mb-0">Theo dõi và lọc trạng thái các đơn hàng bạn đã đặt tại Book Store.</p>
        </div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
            &larr; Tiếp tục mua sắm
        </a>
    </div>

    <!-- Filter Status Tabs -->
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-2">
            <ul class="nav nav-pills flex-wrap gap-1">
                <li class="nav-item">
                    <a class="nav-link ${selectedStatus == 'Tất cả' ? 'active fw-bold' : ''}" 
                       href="${pageContext.request.contextPath}/my-orders">
                        Tất cả
                        <span class="badge ${selectedStatus == 'Tất cả' ? 'bg-light text-dark' : 'bg-secondary'} ms-1">
                            ${statusCounts['Tất cả'] != null ? statusCounts['Tất cả'] : 0}
                        </span>
                    </a>
                </li>
                <c:forEach items="${allStatuses}" var="st">
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == st ? 'active fw-bold' : ''}" 
                           href="${pageContext.request.contextPath}/my-orders?status=${st}">
                            <c:out value="${st}"/>
                            <span class="badge ${selectedStatus == st ? 'bg-light text-dark' : 'bg-secondary'} ms-1">
                                ${statusCounts[st] != null ? statusCounts[st] : 0}
                            </span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="card shadow-sm py-5 text-center">
                <div class="card-body">
                    <div class="display-4 text-muted mb-3">&#128230;</div>
                    <h3 class="h4">Không có đơn hàng nào</h3>
                    <p class="text-muted">
                        <c:choose>
                            <c:when test="${selectedStatus != 'Tất cả'}">
                                Không tìm thấy đơn hàng nào ở trạng thái <strong>"<c:out value="${selectedStatus}"/>"</strong>.
                            </c:when>
                            <c:otherwise>
                                Bạn chưa có đơn hàng nào. Hãy chọn cho mình những cuốn sách yêu thích nhé!
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <c:choose>
                        <c:when test="${selectedStatus != 'Tất cả'}">
                            <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline-primary px-4 py-2 mt-2">
                                Xem tất cả đơn hàng
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4 py-2 mt-2">
                                Mua sắm ngay
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th scope="col" style="width: 8%;">Mã ĐH</th>
                                <th scope="col" style="width: 15%;">Ngày đặt</th>
                                <th scope="col" style="width: 17%;">Người nhận</th>
                                <th scope="col" style="width: 25%;">Địa chỉ nhận hàng</th>
                                <th scope="col" class="text-end" style="width: 13%;">Tổng tiền</th>
                                <th scope="col" class="text-center" style="width: 8%;">Thanh toán</th>
                                <th scope="col" class="text-center" style="width: 14%;">Trạng thái</th>
                                <th scope="col" class="text-center" style="width: 8%;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="ord">
                                <tr>
                                    <td class="fw-bold text-primary">#${ord.id}</td>
                                    <td><small class="text-muted">${ord.createdAt}</small></td>
                                    <td>
                                        <div class="fw-semibold"><c:out value="${ord.recipientName}"/></div>
                                        <small class="text-muted"><c:out value="${ord.recipientPhone}"/></small>
                                    </td>
                                    <td>
                                        <small class="text-truncate d-inline-block" style="max-width: 240px;" title="<c:out value='${ord.recipientAddress}'/>">
                                            <c:out value="${ord.recipientAddress}"/>
                                        </small>
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${ord.totalAmount}" type="number" pattern="#,##0"/> đ
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-light text-dark border">${ord.paymentMethod}</span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge ${ord.statusBadgeClass} px-2 py-1">
                                            <c:out value="${ord.statusDisplayName}"/>
                                        </span>
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
