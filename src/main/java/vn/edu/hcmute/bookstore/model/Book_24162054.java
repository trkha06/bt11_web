package vn.edu.hcmute.bookstore.model;
 import java.math.BigDecimal;
 import java.time.LocalDate;
 public class Book_24162054 {
     private int bookId, isbn, quantity, reviewCount;
     private String title, publisher, description, coverImage, authors;
     private BigDecimal price;
     private LocalDate publishDate;
     public int getBookId(){
        return bookId;
    }
     public void setBookId(int v){
        bookId=v;
    }
     public int getIsbn(){
        return isbn;
    }
     public void setIsbn(int v){
        isbn=v;
    }
     public int getQuantity(){
        return quantity;
    }
     public void setQuantity(int v){
        quantity=v;
    }
     public int getReviewCount(){
        return reviewCount;
    }
     public void setReviewCount(int v){
        reviewCount=v;
    }
     public String getTitle(){
        return title;
    }
     public void setTitle(String v){
        title=v;
    }
     public String getPublisher(){
        return publisher;
    }
     public void setPublisher(String v){
        publisher=v;
    }
     public String getDescription(){
        return description;
    }
     public void setDescription(String v){
        description=v;
    }
     public String getCoverImage(){
        return coverImage;
    }
     public void setCoverImage(String v){
        coverImage=v;
    }
     public String getAuthors(){
        return authors;
    }
     public void setAuthors(String v){
        authors=v;
    }
     public BigDecimal getPrice(){
        return price;
    }
     public void setPrice(BigDecimal v){
        price=v;
    }
     public LocalDate getPublishDate(){
        return publishDate;
    }
     public void setPublishDate(LocalDate v){
        publishDate=v;
    }
 }
