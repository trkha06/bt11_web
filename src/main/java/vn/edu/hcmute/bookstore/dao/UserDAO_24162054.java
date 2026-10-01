package vn.edu.hcmute.bookstore.dao;
 import vn.edu.hcmute.bookstore.model.User_24162054;
 public interface UserDAO_24162054 {
     User_24162054 findByEmail(String email);
     boolean emailExists(String email);
     int insert(User_24162054 user);
     void updateLastLogin(int id);
 }
