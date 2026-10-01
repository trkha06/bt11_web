<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/app.css" rel="stylesheet">
    <sitemesh:write property="head"/>
</head>
<body class="bg-light d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
                &#128218; Book Store
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="nav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang Chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">Sản phẩm</a>
                    </li>
                    <c:if test="${not empty sessionScope.account}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/my-orders">Đơn hàng của tôi</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.account.admin}">
                        <li class="nav-item">
                            <a class="nav-link text-warning fw-semibold" href="${pageContext.request.contextPath}/admin/books">Trang quản trị</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav align-items-center">
                    <li class="nav-item me-3">
                        <a class="nav-link btn btn-outline-light position-relative px-3 text-white" href="${pageContext.request.contextPath}/cart">
                            &#128722; Giỏ hàng
                            <c:if test="${not empty sessionScope.cart and sessionScope.cart.totalQuantity > 0}">
                                <span class="badge rounded-pill bg-danger ms-1">
                                    ${sessionScope.cart.totalQuantity}
                                </span>
                            </c:if>
                        </a>
                    </li>
                    <c:choose>
                        <c:when test="${empty sessionScope.account}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/register">Đăng ký</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-white fw-semibold" href="#" role="button" data-bs-toggle="dropdown">
                                    &#128100; ${sessionScope.account.fullname}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end shadow">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/my-orders">Lịch sử đơn hàng</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <main class="container py-4 flex-grow-1">
        <c:if test="${not empty sessionScope.flash}">
            <div class="alert alert-info alert-dismissible fade show shadow-sm" role="alert">
                ${sessionScope.flash}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="flash" scope="session"/>
        </c:if>
        <sitemesh:write property="body"/>
    </main>

    <footer class="bg-dark text-white text-center py-3 mt-auto">
        <div class="container">
            <div>Họ tên: Võ Văn Trường Kha</div>
            <div class="text-muted small">MSSV: 24162054 | Mã đề: 02</div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
