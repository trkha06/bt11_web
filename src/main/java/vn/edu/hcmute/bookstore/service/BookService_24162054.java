package vn.edu.hcmute.bookstore.service;
 import java.util.*;
 import vn.edu.hcmute.bookstore.model.Book_24162054;
 public interface BookService_24162054 {
     List<Book_24162054> getPage(int page, int size);
     int getTotalPages(int size);
     Book_24162054 getById(int id);
     boolean save(Book_24162054 b);
     boolean delete(int id);
 }
