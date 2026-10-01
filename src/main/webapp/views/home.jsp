<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html>
<head>
    <title>Trang chủ - Book Store</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1">Sản phẩm sách nổi bật</h1>
            <p class="text-muted mb-0">Sách được nhóm theo tác giả - 3 sản phẩm mỗi trang.</p>
        </div>
        <c:if test="${empty sessionScope.account}">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/register">Tạo tài khoản</a>
        </c:if>
    </div>

    <c:if test="${empty books}">
        <div class="alert alert-info">Chưa có sách nào trong hệ thống.</div>
    </c:if>

    <c:set var="previousAuthor" value=""/>
    <div class="row g-4">
        <c:forEach items="${books}" var="book">
            <c:if test="${book.authors ne previousAuthor}">
                <div class="col-12 mt-4">
                    <h2 class="h4 text-primary border-bottom pb-2 mb-0">Tác giả: <c:out value="${book.authors}"/></h2>
                </div>
                <c:set var="previousAuthor" value="${book.authors}"/>
            </c:if>

            <div class="col-md-4">
                <article class="card shadow-sm book-card d-flex flex-column h-100">
                    <a href="${pageContext.request.contextPath}/book-detail?id=${book.bookId}">
                        <img class="card-img-top book-cover" src="${book.coverImage}" alt="Bìa ${book.title}">
                    </a>
                    <div class="card-body d-flex flex-column">
                        <h3 class="h5 mb-2">
                            <a class="text-decoration-none text-dark" href="${pageContext.request.contextPath}/book-detail?id=${book.bookId}">
                                <c:out value="${book.title}"/>
                            </a>
                        </h3>
                        <p class="meta mb-1"><b>Mã ISBN:</b> ${book.isbn}</p>
                        <p class="meta mb-1"><b>Publisher:</b> <c:out value="${book.publisher}"/></p>
                        <p class="meta mb-1"><b>Publisher_date:</b> ${book.publishDate}</p>
                        <p class="meta mb-2">
                            <b>Quantity:</b> 
                            <c:choose>
                                <c:when test="${book.quantity > 0}">
                                    <span class="badge bg-success-subtle text-success border border-success-subtle">${book.quantity} cuốn</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">Hết hàng</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        
                        <div class="d-flex align-items-center justify-content-between my-2">
                            <span class="h5 text-danger fw-bold mb-0">
                                <fmt:formatNumber value="${book.price}" type="number" pattern="#,##0"/> đ
                            </span>
                            <span class="badge text-bg-secondary">Review (${book.reviewCount})</span>
                        </div>

                        <div class="mt-auto pt-3 d-flex gap-2">
                            <a href="${pageContext.request.contextPath}/book-detail?id=${book.bookId}" class="btn btn-sm btn-outline-secondary flex-grow-1">
                                Chi tiết
                            </a>
                            <c:if test="${book.quantity > 0}">
                                <form method="post" action="${pageContext.request.contextPath}/cart" class="d-inline flex-grow-1">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="bookId" value="${book.bookId}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn btn-sm btn-primary w-100">
                                        &#128722; Thêm giỏ
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </article>
            </div>
        </c:forEach>
    </div>

    <nav class="mt-5">
        <ul class="pagination justify-content-center">
            <li class="page-item ${page == 1 ? 'disabled' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/home?page=${page-1}">Trang trước</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${page == i ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/home?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/home?page=${page+1}">Trang sau</a>
            </li>
        </ul>
    </nav>
</body>
</html>
