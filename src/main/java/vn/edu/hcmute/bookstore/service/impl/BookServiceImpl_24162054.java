package vn.edu.hcmute.bookstore.service.impl;
 import java.util.*;
 import vn.edu.hcmute.bookstore.dao.*;
 import vn.edu.hcmute.bookstore.dao.impl.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 public class BookServiceImpl_24162054 implements BookService_24162054 {
     private final BookDAO_24162054 dao=new BookDAOImpl_24162054();
     public List<Book_24162054> getPage(int p, int s){
        return dao.findPage(p, s);
    }
     public int getTotalPages(int s){
        return Math.max(1, (int)Math.ceil(dao.count()/(double)s));
    }
     public Book_24162054 getById(int id){
        return dao.findById(id);
    }
     public boolean save(Book_24162054 b){
        return b.getBookId()==0?dao.insert(b):dao.update(b);
    }
     public boolean delete(int id){
        return dao.delete(id);
    }
 }
