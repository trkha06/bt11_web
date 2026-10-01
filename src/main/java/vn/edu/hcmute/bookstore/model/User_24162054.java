package vn.edu.hcmute.bookstore.model;
 import java.io.Serializable;
 import java.time.LocalDateTime;
 public class User_24162054 implements Serializable {
     private int id;
     private String email, fullname, phone, passwd;
     private LocalDateTime signupDate, lastLogin;
     private boolean admin;
     public int getId(){
        return id;
    }
     public void setId(int v){
        id=v;
    }
     public String getEmail(){
        return email;
    }
     public void setEmail(String v){
        email=v;
    }
     public String getFullname(){
        return fullname;
    }
     public void setFullname(String v){
        fullname=v;
    }
     public String getPhone(){
        return phone;
    }
     public void setPhone(String v){
        phone=v;
    }
     public String getPasswd(){
        return passwd;
    }
     public void setPasswd(String v){
        passwd=v;
    }
     public LocalDateTime getSignupDate(){
        return signupDate;
    }
     public void setSignupDate(LocalDateTime v){
        signupDate=v;
    }
     public LocalDateTime getLastLogin(){
        return lastLogin;
    }
     public void setLastLogin(LocalDateTime v){
        lastLogin=v;
    }
     public boolean isAdmin(){
        return admin;
    }
     public void setAdmin(boolean v){
        admin=v;
    }
 }
