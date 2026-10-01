package vn.edu.hcmute.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vn.edu.hcmute.bookstore.model.Book_24162054;
import vn.edu.hcmute.bookstore.model.Cart_24162054;
import vn.edu.hcmute.bookstore.service.BookService_24162054;
import vn.edu.hcmute.bookstore.service.impl.BookServiceImpl_24162054;

@WebServlet("/cart")
public class CartController_24162054 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookService_24162054 bookService = new BookServiceImpl_24162054();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            // View cart
            request.getRequestDispatcher("/views/cart/cart.jsp").forward(request, response);
            return;
        }

        // Support get-based delete / clear actions if clicked via links
        processAction(request, response, action);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        processAction(request, response, action != null ? action : "add");
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart_24162054 cart = (Cart_24162054) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart_24162054();
            session.setAttribute("cart", cart);
        }

        switch (action) {
            case "add": {
                int bookId = ControllerUtil_24162054.positive(request.getParameter("bookId"), 0);
                if (bookId == 0) {
                    bookId = ControllerUtil_24162054.positive(request.getParameter("id"), 0);
                }
                int quantity = ControllerUtil_24162054.positive(request.getParameter("quantity"), 1);

                Book_24162054 book = bookService.getById(bookId);
                if (book == null) {
                    ControllerUtil_24162054.flash(request, "Sản phẩm không tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/home");
                    return;
                }

                if (book.getQuantity() <= 0) {
                    ControllerUtil_24162054.flash(request, "Sản phẩm \"" + book.getTitle() + "\" hiện đã hết hàng.");
                    response.sendRedirect(request.getHeader("Referer") != null ? request.getHeader("Referer") : (request.getContextPath() + "/home"));
                    return;
                }

                String warning = cart.addItem(book, quantity);
                if (warning != null) {
                    ControllerUtil_24162054.flash(request, warning);
                } else {
                    ControllerUtil_24162054.flash(request, "Đã thêm \"" + book.getTitle() + "\" vào giỏ hàng (" + quantity + " cuốn).");
                }

                String redirect = request.getParameter("redirect");
                if ("cart".equalsIgnoreCase(redirect)) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                } else if ("detail".equalsIgnoreCase(redirect)) {
                    response.sendRedirect(request.getContextPath() + "/book-detail?id=" + bookId);
                } else if ("checkout".equalsIgnoreCase(redirect)) {
                    response.sendRedirect(request.getContextPath() + "/checkout");
                } else {
                    String referer = request.getHeader("Referer");
                    if (referer != null && !referer.isBlank()) {
                        response.sendRedirect(referer);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/cart");
                    }
                }
                break;
            }

            case "update": {
                int bookId = ControllerUtil_24162054.positive(request.getParameter("bookId"), 0);
                int quantity = ControllerUtil_24162054.positive(request.getParameter("quantity"), 1);

                Book_24162054 book = bookService.getById(bookId);
                int stock = (book != null) ? book.getQuantity() : 0;

                String warning = cart.updateItem(bookId, quantity, stock);
                if (warning != null) {
                    ControllerUtil_24162054.flash(request, warning);
                } else {
                    ControllerUtil_24162054.flash(request, "Đã cập nhật số lượng thành công.");
                }
                response.sendRedirect(request.getContextPath() + "/cart");
                break;
            }

            case "delete":
            case "remove": {
                int bookId = ControllerUtil_24162054.positive(request.getParameter("bookId"), 0);
                if (bookId == 0) {
                    bookId = ControllerUtil_24162054.positive(request.getParameter("id"), 0);
                }
                cart.removeItem(bookId);
                ControllerUtil_24162054.flash(request, "Đã xóa sản phẩm khỏi giỏ hàng.");
                response.sendRedirect(request.getContextPath() + "/cart");
                break;
            }

            case "clear": {
                cart.clear();
                ControllerUtil_24162054.flash(request, "Đã xóa toàn bộ sản phẩm trong giỏ hàng.");
                response.sendRedirect(request.getContextPath() + "/cart");
                break;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/cart");
                break;
        }
    }
}
