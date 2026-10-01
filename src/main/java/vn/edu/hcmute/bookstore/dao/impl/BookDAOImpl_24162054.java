package vn.edu.hcmute.bookstore.dao.impl;
 import java.sql.*;
 import java.util.*;
 import vn.edu.hcmute.bookstore.dao.BookDAO_24162054;
 import vn.edu.hcmute.bookstore.model.Book_24162054;
 import vn.edu.hcmute.bookstore.util.DBConnection_24162054;
 public class BookDAOImpl_24162054 implements BookDAO_24162054 {
     private static final String SELECT="SELECT b.*,COALESCE(GROUP_CONCAT(DISTINCT a.author_name ORDER BY a.author_name SEPARATOR ', '),'Chua cap nhat') authors,COUNT(DISTINCT r.userid) review_count FROM books b LEFT JOIN book_author ba ON b.bookid=ba.bookid LEFT JOIN author a ON ba.author_id=a.author_id LEFT JOIN rating r ON b.bookid=r.bookid ";
     public List<Book_24162054> findPage(int page, int size){
        List<Book_24162054> list=new ArrayList<>();
        String sql=SELECT+"GROUP BY b.bookid ORDER BY MIN(a.author_name), b.bookid DESC LIMIT ? OFFSET ?";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1, size);
            p.setInt(2, (page-1)*size);
            try (ResultSet r=p.executeQuery()){
                while (r.next())list.add(map(r));
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
        return list;
    }
     public int count(){
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM books");
        ResultSet r=p.executeQuery()){
            return r.next()?r.getInt(1):0;
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     public Book_24162054 findById(int id){
        String sql=SELECT+"WHERE b.bookid=? GROUP BY b.bookid";
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1, id);
            try (ResultSet r=p.executeQuery()){
                return r.next()?map(r):null;
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     public boolean insert(Book_24162054 b){
        String q="INSERT INTO books(isbn,title,publisher,price,description,publish_date,cover_image,quantity) VALUES(?,?,?,?,?,?,?,?)";
        return change(q, b, false);
    }
     public boolean update(Book_24162054 b){
        String q="UPDATE books SET isbn=?,title=?,publisher=?,price=?,description=?,publish_date=?,cover_image=?,quantity=? WHERE bookid=?";
        return change(q, b, true);
    }
     private boolean change(String q, Book_24162054 b, boolean id){
        try (Connection c=DBConnection_24162054.getConnection();
        PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1, b.getIsbn());
            p.setString(2, b.getTitle());
            p.setString(3, b.getPublisher());
            p.setBigDecimal(4, b.getPrice());
            p.setString(5, b.getDescription());
            p.setDate(6, java.sql.Date.valueOf(b.getPublishDate()));
            p.setString(7, b.getCoverImage());
            p.setInt(8, b.getQuantity());
            if (id)p.setInt(9, b.getBookId());
            return p.executeUpdate()>0;
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     public boolean delete(int id){
        try (Connection c=DBConnection_24162054.getConnection()){
            c.setAutoCommit(false);
            try (PreparedStatement r=c.prepareStatement("DELETE FROM rating WHERE bookid=?");
            PreparedStatement ba=c.prepareStatement("DELETE FROM book_author WHERE bookid=?");
            PreparedStatement b=c.prepareStatement("DELETE FROM books WHERE bookid=?")){
                r.setInt(1, id);
                r.executeUpdate();
                ba.setInt(1, id);
                ba.executeUpdate();
                b.setInt(1, id);
                boolean ok=b.executeUpdate()>0;
                c.commit();
                return ok;
            }
            catch (SQLException e){
                c.rollback();
                throw e;
            }
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
     private Book_24162054 map(ResultSet r)throws SQLException{
        Book_24162054 b=new Book_24162054();
        b.setBookId(r.getInt("bookid"));
        b.setIsbn(r.getInt("isbn"));
        b.setTitle(r.getString("title"));
        b.setPublisher(r.getString("publisher"));
        b.setPrice(r.getBigDecimal("price"));
        b.setDescription(r.getString("description"));
        java.sql.Date d=r.getDate("publish_date");
        b.setPublishDate(d==null?null:d.toLocalDate());
        b.setCoverImage(r.getString("cover_image"));
        b.setQuantity(r.getInt("quantity"));
        b.setAuthors(r.getString("authors"));
        b.setReviewCount(r.getInt("review_count"));
        return b;
    }
 }
