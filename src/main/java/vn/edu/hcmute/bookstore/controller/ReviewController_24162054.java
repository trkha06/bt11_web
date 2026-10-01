package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet("/review") public class ReviewController_24162054 extends HttpServlet {
    private final RatingService_24162054 ratings=new RatingServiceImpl_24162054();
    protected void doPost(HttpServletRequest r, HttpServletResponse p)throws IOException{
        Object x=r.getSession().getAttribute("account");
        int bookId=ControllerUtil_24162054.positive(r.getParameter("bookId"), 0), score=ControllerUtil_24162054.positive(r.getParameter("rating"), 0);
        if (!(x instanceof User_24162054 u)||bookId==0||score>5||ControllerUtil_24162054.clean(r.getParameter("reviewText")).isBlank()){
            p.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        Rating_24162054 v=new Rating_24162054();
        v.setUserId(u.getId());
        v.setBookId(bookId);
        v.setRating(score);
        v.setReviewText(ControllerUtil_24162054.clean(r.getParameter("reviewText")));
        ratings.save(v);
        ControllerUtil_24162054.flash(r, "Review da duoc luu.");
        p.sendRedirect(r.getContextPath()+"/book-detail?id="+bookId);
    }
 }
