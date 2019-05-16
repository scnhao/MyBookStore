package mybookstore.order;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import mybookstore.book.Book;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public void addOrder(Order order){
		try{
			String sql = "insert into orders value(?,?,?,?,?,?)";
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			Object[] params = {order.getOid(),timestamp,order.getTotal(),
					order.getState(),order.getOwner().getUid(),order.getAddress()};
			qr.update(sql, params);
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	
	public void addOrderItemList(List<OrderItem> orderitemlist){
		try{
			String sql = "insert into orderitem value(?,?,?,?,?)";
			Object[][] params = new Object[orderitemlist.size()][];
			for(int i = 0; i<orderitemlist.size();i++){
				OrderItem item = orderitemlist.get(i);
				params[i] = new Object[]{item.getIid(),item.getCount(),item.getSubtotal(),
						item.getOrder().getOid(),item.getBook().getBid()};
				
				}
			qr.batch(sql, params);
			}catch (SQLException e){
				throw new RuntimeException();
		}	
	}
	public List<Order> findByUid(String uid){
		try{
			String sql = "select * from orders where uid = ?";
			List<Order> orderlist = qr.query(sql, 
					new BeanListHandler<Order>(Order.class), uid);
			for (Order order:orderlist){
				loadOrderItems(order);
			}
			return orderlist;
			
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	private void loadOrderItems(Order order) throws SQLException{
		String sql = "select * from orderitem i , book b where i.bid = b.bid and oid = ?";
		List<Map<String, Object>> maplist = qr.query(
				sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(maplist);
		order.setOrderItemList(orderItemList);
	}

	private List<OrderItem> toOrderItemList(List<Map<String, Object>> maplist) {
		List<OrderItem> orderitemlist = new ArrayList<OrderItem>();
		for (Map<String, Object> map : maplist){
			OrderItem orderitem = toOrderItem(map);
			orderitemlist.add(orderitem);
		}
		return orderitemlist;
	}

	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderitem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderitem.setBook(book);
		return orderitem;
	}
	
	public Order load(String oid){
		try{
			String sql = "select * from orders where oid = ?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			loadOrderItems(order);
			return order;
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public int getStateByOid(String oid){
		try{
			String sql = "select state from orders where oid = ?";
			Number num = (Number)qr.query(sql, new ScalarHandler(), oid);
			return num.intValue();
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public void updateState(String oid,int state){
		try{
			String sql = "update orders set state = ? where oid = ?";
			qr.update(sql,state,oid);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
}
