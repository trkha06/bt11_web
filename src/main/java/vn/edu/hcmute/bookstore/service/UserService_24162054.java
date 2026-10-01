package vn.edu.hcmute.bookstore.service;
 import vn.edu.hcmute.bookstore.model.User_24162054;
 public interface UserService_24162054 {
     User_24162054 login(String email, String password);
     boolean emailExists(String email);
     int register(User_24162054 user);
 }
