package mybookstore.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import mybookstore.cart.Cart;
import mybookstore.cart.CartItem;
import mybookstore.user.User;

public class OrderServlet extends BaseServlet {
	public OrderService orderservice = new OrderService();
	
	public String confirm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		try{
			orderservice.confirm(oid);
			request.setAttribute("msg", "已经确认收货");
		}catch (OrderException e){
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	
	public String load(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		Order order = orderservice.load(oid);
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}
	
	public String myOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("session_user");
		String uid = user.getUid();
		List<Order> myOrderList = orderservice.findByUid(uid);
		request.setAttribute("myOrderList", myOrderList);
		return "f:/jsps/order/list.jsp";
		
	}
	public String add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setState(1);
		order.setOwner((User)request.getSession().getAttribute("session_user"));
		order.setTotal(cart.getTotal());
		
		List<OrderItem> orderitemlist = new ArrayList<OrderItem>();
		for(CartItem cartitem:cart.getCartItems()){
			OrderItem orderitem = new OrderItem();
			orderitem.setIid(CommonUtils.uuid());
			orderitem.setCount(cartitem.getCount());
			orderitem.setSubtotal(cartitem.getSubtotal());
			orderitem.setOrder(order);
			orderitem.setBook(cartitem.getBook());
			
			orderitemlist.add(orderitem);
		}
		order.setOrderItemList(orderitemlist);
		orderservice.add(order);
		request.setAttribute("order", order);
		cart.clear();
		return "f:/jsps/order/desc.jsp";
	}
	

}
