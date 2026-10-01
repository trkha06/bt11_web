package vn.edu.hcmute.bookstore.service;
 import java.util.*;
 import vn.edu.hcmute.bookstore.model.Rating_24162054;
 public interface RatingService_24162054 {
     List<Rating_24162054> getByBook(int id);
     boolean save(Rating_24162054 rating);
 }
