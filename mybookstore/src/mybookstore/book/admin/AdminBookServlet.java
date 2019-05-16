package mybookstore.book.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import mybookstore.book.Book;
import mybookstore.book.BookService;
import mybookstore.category.CategoryService;

public class AdminBookServlet extends BaseServlet {
	BookService bookservice = new BookService();
	CategoryService categoryservice = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<Book> booklist = bookservice.findAll();
		request.setAttribute("bookList", booklist);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	public String load(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = bookservice.load(bid);
		request.setAttribute("book", book);
		request.setAttribute("categoryList", categoryservice.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	public String addPre(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("categoryList", categoryservice.findAll());
		return "f:/adminjsps/admin/book/add.jsp";
	}
	public String delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid =request.getParameter("bid");
		bookservice.delete(bid);
		return findAll(request, response);
	}
	public String edit(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		bookservice.edit(book);
		return findAll(request, response);
	}
	

}
