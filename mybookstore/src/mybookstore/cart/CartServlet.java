package mybookstore.cart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import mybookstore.book.Book;
import mybookstore.book.BookService;

public class CartServlet extends BaseServlet{

	public String add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		Book book = new BookService().load(bid);
		int count = Integer.parseInt(request.getParameter("count"));
		CartItem cartitem = new CartItem();
		cartitem.setBook(book);
		cartitem.setCount(count);
		cart.add(cartitem);
		return "f:/jsps/cart/list.jsp";
	}
	public String clear(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
	public String delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
}
