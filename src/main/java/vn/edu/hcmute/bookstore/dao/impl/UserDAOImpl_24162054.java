package vn.edu.hcmute.bookstore.dao.impl;
 import java.sql.*;
 import vn.edu.hcmute.bookstore.dao.UserDAO_24162054;
 import vn.edu.hcmute.bookstore.model.User_24162054;
 import vn.edu.hcmute.bookstore.util.DBConnection_24162054;
 public class UserDAOImpl_24162054 implements UserDAO_24162054 {
     public User_24162054 findByEmail(String email){
        String sql="SELECT * FROM users WHERE email=?";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1, email);
            try (ResultSet r=p.executeQuery()){
                return r.next()?map(r):null;
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     public boolean emailExists(String e){
        return findByEmail(e)!=null;
    }
     public int insert(User_24162054 u){
        String sql="INSERT INTO users(email,fullname,phone,passwd,signup_date,is_admin) VALUES(?,?,?,?,NOW(),b'0')";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            p.setString(1, u.getEmail());
            p.setString(2, u.getFullname());
            p.setInt(3, Integer.parseInt(u.getPhone()));
            p.setString(4, u.getPasswd());
            p.executeUpdate();
            try (ResultSet r=p.getGeneratedKeys()){
                return r.next()?r.getInt(1):0;
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     public void updateLastLogin(int id){
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement("UPDATE users SET last_login=NOW() WHERE id=?")){
            p.setInt(1, id);
            p.executeUpdate();
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     private User_24162054 map(ResultSet r)throws SQLException{
        User_24162054 u=new User_24162054();
        u.setId(r.getInt("id"));
        u.setEmail(r.getString("email"));
        u.setFullname(r.getString("fullname"));
        u.setPhone(r.getString("phone"));
        u.setPasswd(r.getString("passwd"));
        u.setAdmin(r.getBoolean("is_admin"));
        return u;
    }
 }
