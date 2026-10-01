package vn.edu.hcmute.bookstore.dao;
 import java.util.*;
 import vn.edu.hcmute.bookstore.model.Book_24162054;
 public interface BookDAO_24162054 {
     List<Book_24162054> findPage(int page, int size);
     int count();
     Book_24162054 findById(int id);
     boolean insert(Book_24162054 book);
     boolean update(Book_24162054 book);
     boolean delete(int id);
 }
