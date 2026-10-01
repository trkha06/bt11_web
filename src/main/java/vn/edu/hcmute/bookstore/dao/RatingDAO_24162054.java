package vn.edu.hcmute.bookstore.dao;
 import java.util.*;
 import vn.edu.hcmute.bookstore.model.Rating_24162054;
 public interface RatingDAO_24162054 {
     List<Rating_24162054> findByBookId(int bookId);
     boolean upsert(Rating_24162054 rating);
 }
