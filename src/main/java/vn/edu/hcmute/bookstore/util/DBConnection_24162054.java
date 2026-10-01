package vn.edu.hcmute.bookstore.util;
 import java.io.*;
 import java.sql.*;
 import java.util.Properties;
 public final class DBConnection_24162054 {
     private DBConnection_24162054(){
    }
     public static Connection getConnection() throws SQLException {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
         }
         catch (ClassNotFoundException e) {
             throw new SQLException("Thiếu MySQL Connector/J trong WEB-INF/lib. Hãy deploy lại file WAR mới.", e);
         }
         Properties p=new Properties();
         try (InputStream in=DBConnection_24162054.class.getClassLoader().getResourceAsStream("db.properties")){
            if (in!=null)p.load(in);
        }
         catch (IOException e){
            throw new SQLException("Cannot read db.properties", e);
        }
         String url=System.getProperty("app.jdbc.url", p.getProperty("jdbc.url"));
         String user=System.getProperty("app.jdbc.username", p.getProperty("jdbc.username"));
         String pass=System.getProperty("app.jdbc.password", p.getProperty("jdbc.password"));
         return DriverManager.getConnection(url, user, pass);
     }
 }
