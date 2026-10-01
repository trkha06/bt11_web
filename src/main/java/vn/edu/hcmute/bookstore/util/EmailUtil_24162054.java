package vn.edu.hcmute.bookstore.util;
 import jakarta.mail.*;
 import jakarta.mail.internet.*;
 import java.io.*;
 import java.util.*;
 public final class EmailUtil_24162054 {
     private EmailUtil_24162054(){
    }
     public static boolean sendOtp(String recipient, String otp){
        Properties c=new Properties();
        try (InputStream in=EmailUtil_24162054.class.getClassLoader().getResourceAsStream("email.properties")){
            if (in==null){
                System.err.println("OTP for "+recipient+": "+otp+" (create email.properties from example)");
                return false;
            }
            c.load(in);
        }
        catch (IOException e){
            return false;
        }
         String user=c.getProperty("mail.smtp.user"), pass=c.getProperty("mail.smtp.password", "").replaceAll("\\s+", "");
         Properties p=new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", c.getProperty("mail.smtp.host", "smtp.gmail.com"));
        p.put("mail.smtp.port", c.getProperty("mail.smtp.port", "587"));
         try{
            Session s=Session.getInstance(p, new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(user, pass);
                }
            }
            );
            Message m=new MimeMessage(s);
            m.setFrom(new InternetAddress(user));
            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            m.setSubject("Book Store - OTP kich hoat tai khoan");
            m.setText("Ma OTP cua ban la: "+otp+". Ma co hieu luc trong 10 phut.");
            Transport.send(m);
            return true;
        }
        catch (MessagingException e){
            System.err.println("Email error: "+e.getMessage());
            return false;
        }
     }
 }
