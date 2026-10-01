package vn.edu.hcmute.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem_24162054 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Book_24162054 book;
    private int quantity;

    public CartItem_24162054() {
    }

    public CartItem_24162054(Book_24162054 book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book_24162054 getBook() {
        return book;
    }

    public void setBook(Book_24162054 book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return book != null && book.getPrice() != null ? book.getPrice() : BigDecimal.ZERO;
    }

    public BigDecimal getTotalPrice() {
        return getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
