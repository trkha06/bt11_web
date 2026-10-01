package vn.edu.hcmute.bookstore.dao.impl;
 import java.sql.*;
 import java.util.*;
 import vn.edu.hcmute.bookstore.dao.RatingDAO_24162054;
 import vn.edu.hcmute.bookstore.model.Rating_24162054;
 import vn.edu.hcmute.bookstore.util.DBConnection_24162054;
 public class RatingDAOImpl_24162054 implements RatingDAO_24162054 {
     public List<Rating_24162054> findByBookId(int id){
        List<Rating_24162054> x=new ArrayList<>();
        String q="SELECT r.*,u.fullname FROM rating r JOIN users u ON r.userid=u.id WHERE r.bookid=? ORDER BY u.fullname";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1, id);
            try (ResultSet s=p.executeQuery()){
                while (s.next()){
                    Rating_24162054 r=new Rating_24162054();
                    r.setUserId(s.getInt("userid"));
                    r.setBookId(s.getInt("bookid"));
                    r.setRating(s.getInt("rating"));
                    r.setReviewText(s.getString("review_text"));
                    r.setUserFullname(s.getString("fullname"));
                    x.add(r);
                }
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
        return x;
    }
     public boolean upsert(Rating_24162054 r){
        String q="INSERT INTO rating(userid,bookid,rating,review_text) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE rating=VALUES(rating),review_text=VALUES(review_text)";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1, r.getUserId());
            p.setInt(2, r.getBookId());
            p.setInt(3, r.getRating());
            p.setString(4, r.getReviewText());
            return p.executeUpdate()>0;
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
 }
