package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet("/home") public class HomeController_24162054 extends HttpServlet {
     private final BookService_24162054 books=new BookServiceImpl_24162054();
     protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        int total=books.getTotalPages(3), page=Math.min(ControllerUtil_24162054.positive(r.getParameter("page"), 1), total);
        r.setAttribute("books", books.getPage(page, 3));
        r.setAttribute("page", page);
        r.setAttribute("totalPages", total);
        r.getRequestDispatcher("/views/home.jsp").forward(r, p);
    }
 }
