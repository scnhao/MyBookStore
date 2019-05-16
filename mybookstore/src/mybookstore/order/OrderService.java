package mybookstore.order;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	public OrderDao orderdao = new OrderDao();
	public void add(Order order){
		try{
			JdbcUtils.beginTransaction();
			orderdao.addOrder(order);
			orderdao.addOrderItemList(order.getOrderItemList());
			JdbcUtils.commitTransaction();
		}catch (SQLException e){
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				throw new RuntimeException();
			}
		}
	}	
	public List<Order> findByUid(String uid){
		return orderdao.findByUid(uid);
	}
	public Order load(String oid){
		return orderdao.load(oid);
	}
	public void confirm(String oid) throws OrderException{
		if(orderdao.getStateByOid(oid) != 3) throw new OrderException("您还未付款，请先付款");
		orderdao.updateState(oid, 4);
	}
}

