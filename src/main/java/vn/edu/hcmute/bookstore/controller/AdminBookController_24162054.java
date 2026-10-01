package vn.edu.hcmute.bookstore.controller;
 import jakarta.servlet.*;
 import jakarta.servlet.annotation.*;
 import jakarta.servlet.http.*;
 import java.io.*;
 import vn.edu.hcmute.bookstore.model.*;
 import vn.edu.hcmute.bookstore.service.*;
 import vn.edu.hcmute.bookstore.service.impl.*;
 @WebServlet({
    "/admin/books", "/admin/books/add", "/admin/books/edit", "/admin/books/delete"
}
) public class AdminBookController_24162054 extends HttpServlet {
     private final BookService_24162054 books=new BookServiceImpl_24162054();
     protected void doGet(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        String path=r.getServletPath();
        if (path.endsWith("/add")){
            form(r, p, new Book_24162054());
            return;
        }
        if (path.endsWith("/edit")){
            Book_24162054 b=books.getById(ControllerUtil_24162054.positive(r.getParameter("id"), 0));
            if (b==null){
                p.sendError(404);
                return;
            }
            form(r, p, b);
            return;
        }
        int total=books.getTotalPages(5), page=Math.min(ControllerUtil_24162054.positive(r.getParameter("page"), 1), total);
        r.setAttribute("books", books.getPage(page, 5));
        r.setAttribute("page", page);
        r.setAttribute("totalPages", total);
        r.getRequestDispatcher("/views/admin/books.jsp").forward(r, p);
    }
     protected void doPost(HttpServletRequest r, HttpServletResponse p)throws ServletException, IOException{
        String path=r.getServletPath();
        if (path.endsWith("/delete")){
            books.delete(ControllerUtil_24162054.positive(r.getParameter("id"), 0));
            ControllerUtil_24162054.flash(r, "Da xoa sach va du lieu lien quan.");
            p.sendRedirect(r.getContextPath()+"/admin/books");
            return;
        }
        try{
            Book_24162054 b=ControllerUtil_24162054.bookFrom(r);
            if (b.getTitle().isBlank()||b.getIsbn()==0||b.getQuantity()<0||b.getPrice().signum()<0)throw new IllegalArgumentException();
            books.save(b);
            ControllerUtil_24162054.flash(r, "Da luu sach thanh cong.");
            p.sendRedirect(r.getContextPath()+"/admin/books");
        }
        catch (Exception e){
            r.setAttribute("error", "Vui long nhap day du du lieu hop le (ngay yyyy-MM-dd).");
            try{
                form(r, p, ControllerUtil_24162054.bookFrom(r));
            }
            catch (Exception ignored){
                form(r, p, new Book_24162054());
            }
        }
    }
     private void form(HttpServletRequest r, HttpServletResponse p, Book_24162054 b)throws ServletException, IOException{
        r.setAttribute("book", b);
        r.getRequestDispatcher("/views/admin/book-form.jsp").forward(r, p);
    }
 }
