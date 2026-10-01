package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet("/book-detail") public class BookDetailController_24162054 extends HttpServlet {
    private final BookService_24162054 books=new BookServiceImpl_24162054();
    private final RatingService_24162054 ratings=new RatingServiceImpl_24162054();
    protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        int id=ControllerUtil_24162054.positive(r.getParameter("id"), 0);
        if (id==0||books.getById(id)==null){
            p.sendError(404);
            return;
        }
        r.setAttribute("book", books.getById(id));
        r.setAttribute("reviews", ratings.getByBook(id));
        r.getRequestDispatcher("/views/book/detail.jsp").forward(r, p);
    }
 }
