<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title><c:out value="${book.title}"/> - Book Store</title>
</head>
<body>
    <div class="row g-4">
        <div class="col-md-4">
            <img class="img-fluid rounded shadow-sm w-100" src="${book.coverImage}" alt="Bìa ${book.title}" style="max-height: 450px; object-fit: cover;">
        </div>
        <div class="col-md-8">
            <h1 class="h2"><c:out value="${book.title}"/></h1>
            <p><b>Mã ISBN:</b> ${book.isbn}</p>
            <p><b>Tác giả:</b> <c:out value="${book.authors}"/></p>
            <p><b>Publisher:</b> <c:out value="${book.publisher}"/></p>
            <p><b>Publisher_date:</b> ${book.publishDate}</p>
            <p>
                <b>Quantity:</b> 
                <c:choose>
                    <c:when test="${book.quantity > 0}">
                        <span class="badge bg-success">${book.quantity} cuốn (Còn hàng)</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge bg-danger">Hết hàng</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><b>Reviews:</b> ${book.reviewCount} lượt đánh giá</p>
            
            <div class="my-3 p-3 bg-light rounded border">
                <div class="d-flex align-items-baseline mb-2">
                    <span class="text-muted me-2">Giá bán:</span>
                    <span class="h3 text-danger fw-bold mb-0">
                        <fmt:formatNumber value="${book.price}" type="number" pattern="#,##0"/> đ
                    </span>
                </div>

                <c:choose>
                    <c:when test="${book.quantity > 0}">
                        <form method="post" action="${pageContext.request.contextPath}/cart" class="row g-3 align-items-center mt-1">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="bookId" value="${book.bookId}">
                            <div class="col-auto">
                                <label class="form-label mb-0 fw-semibold">Số lượng mua:</label>
                            </div>
                            <div class="col-auto">
                                <div class="input-group" style="width: 140px;">
                                    <button class="btn btn-outline-secondary" type="button" onclick="const input=this.nextElementSibling; if(parseInt(input.value)>1) input.value=parseInt(input.value)-1;">-</button>
                                    <input type="number" name="quantity" value="1" min="1" max="${book.quantity}" class="form-control text-center" required>
                                    <button class="btn btn-outline-secondary" type="button" onclick="const input=this.previousElementSibling; if(parseInt(input.value)<${book.quantity}) input.value=parseInt(input.value)+1; else alert('Số lượng đã đạt giới hạn tồn kho (${book.quantity} cuốn).');">+</button>
                                </div>
                            </div>
                            <div class="col-12 d-flex gap-2 mt-3">
                                <button type="submit" name="redirect" value="cart" class="btn btn-primary px-4">
                                    &#128722; Thêm vào giỏ hàng
                                </button>
                                <button type="submit" name="redirect" value="checkout" class="btn btn-danger px-4">
                                    Mua ngay (COD)
                                </button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-warning mb-0 mt-2">
                            Sản phẩm này hiện đang tạm hết hàng. Vui lòng quay lại sau!
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <h5 class="mt-4">Mô tả sản phẩm:</h5>
            <p class="text-muted"><c:out value="${book.description}"/></p>
        </div>
    </div>

    <hr class="my-4">
    <h2 class="h4">Reviews</h2>
    <c:forEach items="${reviews}" var="review">
        <div class="review">
            <b><c:out value="${review.userFullname}"/></b> <span class="text-warning">(${review.rating}/5 &#9733;)</span>: <c:out value="${review.reviewText}"/>
        </div>
    </c:forEach>
    <c:if test="${empty reviews}">
        <p class="text-muted">Chưa có review nào cho cuốn sách này.</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty sessionScope.account}">
            <div class="card mt-4">
                <div class="card-body">
                    <h3 class="h5">Thêm / cập nhật review</h3>
                    <form method="post" action="${pageContext.request.contextPath}/review">
                        <input type="hidden" name="bookId" value="${book.bookId}">
                        <div class="mb-3">
                            <label class="form-label">Rating</label>
                            <select name="rating" class="form-select" required>
                                <option value="5">5 - Rất hay</option>
                                <option value="4">4 - Hay</option>
                                <option value="3">3 - Bình thường</option>
                                <option value="2">2 - Chưa tốt</option>
                                <option value="1">1 - Không phù hợp</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Review text</label>
                            <textarea name="reviewText" class="form-control" required maxlength="2000"></textarea>
                        </div>
                        <button class="btn btn-primary">Gửi đánh giá</button>
                    </form>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <p class="mt-4">Bạn cần <a href="${pageContext.request.contextPath}/login">đăng nhập</a> để gửi review.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
