package vn.edu.hcmute.bookstore.util;
 import java.nio.charset.StandardCharsets;
 import java.security.*;
 public final class PasswordUtil_24162054 {
     private PasswordUtil_24162054(){
    }
     public static String md5(String input){
        try{
            byte[] b=MessageDigest.getInstance("MD5").digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder s=new StringBuilder();
            for (byte x:b)s.append(String.format("%02x", x));
            return s.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new IllegalStateException(e);
        }
    }
 }
