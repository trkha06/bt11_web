package vn.edu.hcmute.bookstore.service.impl;
 import vn.edu.hcmute.bookstore.dao.*;
 import vn.edu.hcmute.bookstore.dao.impl.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.util.PasswordUtil_24162054;
 public class UserServiceImpl_24162054 implements UserService_24162054 {
     private final UserDAO_24162054 dao=new UserDAOImpl_24162054();
     public User_24162054 login(String email, String password){
        User_24162054 u=dao.findByEmail(email);
        if (u!=null&&u.getPasswd().equals(PasswordUtil_24162054.md5(password))){
            dao.updateLastLogin(u.getId());
            return u;
        }
        return null;
    }
     public boolean emailExists(String email){
        return dao.emailExists(email);
    }
     public int register(User_24162054 u){
        u.setPasswd(PasswordUtil_24162054.md5(u.getPasswd()));
        return dao.insert(u);
    }
 }
