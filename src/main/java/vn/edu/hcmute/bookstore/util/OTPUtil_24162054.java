package vn.edu.hcmute.bookstore.util;
 import java.security.SecureRandom;
 public final class OTPUtil_24162054 {
     private OTPUtil_24162054(){
    }
     public static String generate(){
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }
 }
