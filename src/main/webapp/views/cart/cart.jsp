<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Giỏ hàng - Book Store</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1">Giỏ hàng của bạn</h1>
            <p class="text-muted mb-0">Quản lý và cập nhật các sản phẩm trước khi thanh toán.</p>
        </div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
            &larr; Tiếp tục mua sắm
        </a>
    </div>

    <c:choose>
        <c:when test="${empty sessionScope.cart or empty sessionScope.cart.items or sessionScope.cart.totalQuantity == 0}">
            <div class="card shadow-sm py-5 text-center">
                <div class="card-body">
                    <div class="display-4 text-muted mb-3">&#128722;</div>
                    <h3 class="h4">Giỏ hàng của bạn đang trống</h3>
                    <p class="text-muted">Bạn chưa có cuốn sách nào trong giỏ hàng. Hãy khám phá kho sách của chúng tôi ngay nhé!</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4 py-2 mt-2">
                        Khám phá sách ngay
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th scope="col" style="width: 45%;">Sản phẩm</th>
                                        <th scope="col" class="text-center" style="width: 15%;">Đơn giá</th>
                                        <th scope="col" class="text-center" style="width: 20%;">Số lượng</th>
                                        <th scope="col" class="text-end" style="width: 15%;">Thành tiền</th>
                                        <th scope="col" class="text-center" style="width: 5%;"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sessionScope.cart.items}" var="item">
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="${item.book.coverImage}" alt="Bìa ${item.book.title}" class="rounded shadow-sm me-3" style="width: 55px; height: 75px; object-fit: cover;">
                                                    <div>
                                                        <h6 class="mb-1">
                                                            <a href="${pageContext.request.contextPath}/book-detail?id=${item.book.bookId}" class="text-decoration-none text-dark fw-bold">
                                                                <c:out value="${item.book.title}"/>
                                                            </a>
                                                        </h6>
                                                        <small class="text-muted d-block">Tác giả: <c:out value="${item.book.authors}"/></small>
                                                        <small class="badge bg-light text-secondary border mt-1">Còn lại: ${item.book.quantity} cuốn</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="text-center fw-semibold text-primary">
                                                <fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,##0"/> đ
                                            </td>
                                            <td>
                                                <form method="post" action="${pageContext.request.contextPath}/cart" class="d-flex align-items-center justify-content-center">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="bookId" value="${item.book.bookId}">
                                                    <div class="input-group input-group-sm" style="max-width: 130px;">
                                                        <button class="btn btn-outline-secondary btn-qty-minus" type="button" onclick="decreaseQty(this)">-</button>
                                                        <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.quantity}" class="form-control text-center qty-input" onchange="this.form.submit()" required>
                                                        <button class="btn btn-outline-secondary btn-qty-plus" type="button" onclick="increaseQty(this, ${item.book.quantity})">+</button>
                                                    </div>
                                                </form>
                                            </td>
                                            <td class="text-end fw-bold text-danger">
                                                <fmt:formatNumber value="${item.totalPrice}" type="number" pattern="#,##0"/> đ
                                            </td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/cart?action=delete&bookId=${item.book.bookId}" class="btn btn-sm btn-outline-danger" title="Xóa khỏi giỏ" onclick="return confirm('Bạn có chắc muốn xóa cuốn sách này khỏi giỏ hàng?');">
                                                    &times;
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer bg-white d-flex justify-content-between py-3">
                            <a href="${pageContext.request.contextPath}/cart?action=clear" class="btn btn-outline-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa toàn bộ giỏ hàng?');">
                                Xóa toàn bộ giỏ hàng
                            </a>
                            <small class="text-muted align-self-center">* Lưu ý: Số lượng điều chỉnh không được vượt quá số lượng tồn kho.</small>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-primary text-white py-3">
                            <h5 class="card-title mb-0">Tóm tắt đơn hàng</h5>
                        </div>
                        <div class="card-body">
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tổng số lượng sách:</span>
                                <span class="fw-semibold">${sessionScope.cart.totalQuantity} cuốn</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3">
                                <span class="text-muted">Phương thức thanh toán:</span>
                                <span class="badge bg-success">COD (Khi nhận hàng)</span>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <span class="h5 mb-0">Tổng thanh toán:</span>
                                <span class="h4 text-danger fw-bold mb-0">
                                    <fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number" pattern="#,##0"/> đ
                                </span>
                            </div>

                            <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success btn-lg w-100 fw-bold shadow-sm">
                                Tiến hành thanh toán COD &rarr;
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <script>
        function decreaseQty(btn) {
            const form = btn.closest('form');
            const input = form.querySelector('.qty-input');
            let val = parseInt(input.value) || 1;
            if (val > 1) {
                input.value = val - 1;
                form.submit();
            }
        }
        function increaseQty(btn, maxStock) {
            const form = btn.closest('form');
            const input = form.querySelector('.qty-input');
            let val = parseInt(input.value) || 1;
            if (val < maxStock) {
                input.value = val + 1;
                form.submit();
            } else {
                alert('Số lượng trong giỏ hàng đã đạt giới hạn tồn kho tối đa (' + maxStock + ' cuốn).');
            }
        }
    </script>
</body>
</html>
