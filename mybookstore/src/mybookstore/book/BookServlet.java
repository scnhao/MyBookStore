package mybookstore.book;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
	private BookService bookservice = new BookService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("bookList", bookservice.findAll());
		return "f:/jsps/book/list.jsp";
	}
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("bookList", bookservice.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	public String load(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		request.setAttribute("book", bookservice.load(bid));
		return "f:/jsps/book/desc.jsp";
	}
}
