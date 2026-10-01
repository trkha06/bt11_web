package vn.edu.hcmute.bookstore.service.impl;
 import java.util.*;
 import vn.edu.hcmute.bookstore.dao.*;
 import vn.edu.hcmute.bookstore.dao.impl.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 public class RatingServiceImpl_24162054 implements RatingService_24162054 {
     private final RatingDAO_24162054 dao=new RatingDAOImpl_24162054();
     public List<Rating_24162054> getByBook(int id){
        return dao.findByBookId(id);
    }
     public boolean save(Rating_24162054 r){
        return dao.upsert(r);
    }
 }
