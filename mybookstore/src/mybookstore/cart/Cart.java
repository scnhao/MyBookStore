package mybookstore.cart;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	
	public double getTotal(){
		double total = 0;
		for (CartItem cartitem : map.values()) {
			total += cartitem.getSubtotal();
		}
		return total;
	}
	public void add(CartItem cartitem){
		if(map.containsKey(cartitem.getBook().getBid())){
			CartItem _cartitem = map.get(cartitem.getBook().getBid());
			_cartitem.setCount(_cartitem.getCount() + cartitem.getCount()); 
			map.put(cartitem.getBook().getBid(), _cartitem);
		}else {
			map.put(cartitem.getBook().getBid(), cartitem);
		}
	}
	
	public void delete(String bid){
		map.remove(bid);
	}
	
	public void clear(){
		map.clear();
		
	}
	
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}
