package vn.edu.hcmute.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart_24162054 implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<Integer, CartItem_24162054> items = new LinkedHashMap<>();

    public Cart_24162054() {
    }

    public Map<Integer, CartItem_24162054> getItemsMap() {
        return items;
    }

    public Collection<CartItem_24162054> getItems() {
        return items.values();
    }

    public CartItem_24162054 getItem(int bookId) {
        return items.get(bookId);
    }

    /**
     * Adds a quantity of book to the cart respecting stock limit.
     * @return message describing result or null if successful normally
     */
    public String addItem(Book_24162054 book, int requestedQty) {
        if (book == null) {
            return "Sản phẩm không tồn tại.";
        }
        if (book.getQuantity() <= 0) {
            return "Sản phẩm \"" + book.getTitle() + "\" hiện đã hết hàng.";
        }
        if (requestedQty <= 0) {
            requestedQty = 1;
        }

        int maxStock = book.getQuantity();
        CartItem_24162054 existing = items.get(book.getBookId());
        int currentQty = (existing != null) ? existing.getQuantity() : 0;
        int newQty = currentQty + requestedQty;

        String warning = null;
        if (newQty > maxStock) {
            newQty = maxStock;
            warning = "Số lượng trong giỏ hàng cho \"" + book.getTitle() + "\" đã được điều chỉnh về mức tồn kho tối đa (" + maxStock + " cuốn).";
        }

        if (existing != null) {
            existing.setQuantity(newQty);
            existing.setBook(book);
        } else {
            items.put(book.getBookId(), new CartItem_24162054(book, newQty));
        }

        return warning;
    }

    /**
     * Updates an item's quantity respecting stock limit.
     * @return warning message if quantity had to be adjusted
     */
    public String updateItem(int bookId, int requestedQty, int maxStock) {
        CartItem_24162054 existing = items.get(bookId);
        if (existing == null) {
            return null;
        }

        if (requestedQty <= 0) {
            items.remove(bookId);
            return "Đã xóa sản phẩm khỏi giỏ hàng.";
        }

        if (maxStock <= 0) {
            items.remove(bookId);
            return "Sản phẩm hiện đã hết hàng và đã được xóa khỏi giỏ.";
        }

        String warning = null;
        if (requestedQty > maxStock) {
            existing.setQuantity(maxStock);
            warning = "Số lượng của \"" + existing.getBook().getTitle() + "\" đã được điều chỉnh về mức tồn kho tối đa (" + maxStock + " cuốn).";
        } else {
            existing.setQuantity(requestedQty);
        }

        return warning;
    }

    public void removeItem(int bookId) {
        items.remove(bookId);
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalQuantity() {
        int total = 0;
        for (CartItem_24162054 item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24162054 item : items.values()) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }
}
