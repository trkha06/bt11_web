package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.http.*;
 import java.time.*;
 import java.math.*;
 import vn.edu.hcmute.bookstore.model.Book_24162054;
 public final class ControllerUtil_24162054 {
     private ControllerUtil_24162054(){
    }
     public static int positive(String value, int fallback){
        try{
            int n=Integer.parseInt(value);
            return n>0?n:fallback;
        }
        catch (Exception e){
            return fallback;
        }
    }
     public static void flash(HttpServletRequest r, String message){
        r.getSession().setAttribute("flash", message);
    }
     public static String clean(String s){
        return s==null?"":s.trim();
    }
     public static Book_24162054 bookFrom(HttpServletRequest r){
        Book_24162054 b=new Book_24162054();
        b.setBookId(positive(r.getParameter("bookId"), 0));
        b.setIsbn(positive(r.getParameter("isbn"), 0));
        b.setTitle(clean(r.getParameter("title")));
        b.setPublisher(clean(r.getParameter("publisher")));
        b.setPrice(new BigDecimal(clean(r.getParameter("price"))));
        b.setDescription(clean(r.getParameter("description")));
        b.setPublishDate(LocalDate.parse(clean(r.getParameter("publishDate"))));
        b.setCoverImage(clean(r.getParameter("coverImage")));
        b.setQuantity(positive(r.getParameter("quantity"), 0));
        return b;
    }
 }
