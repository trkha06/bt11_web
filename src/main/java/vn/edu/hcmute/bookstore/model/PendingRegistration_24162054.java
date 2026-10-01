package vn.edu.hcmute.bookstore.model;
 import java.io.Serializable;
 public class PendingRegistration_24162054 implements Serializable {
     private final User_24162054 user;
     private final String otp;
     private final long expiresAt;
     public PendingRegistration_24162054(User_24162054 u, String o, long e){
        user=u;
        otp=o;
        expiresAt=e;
    }
     public User_24162054 getUser(){
        return user;
    }
     public String getOtp(){
        return otp;
    }
     public long getExpiresAt(){
        return expiresAt;
    }
 }
