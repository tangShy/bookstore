package cn.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.itcast.bookstore.cart.web.servlet.CartItem;

public class Cart {
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
	
	//合计=所有条目小计之和
	public double getTotal() {
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : map.values()) {
			BigDecimal subtotal = new BigDecimal("" + cartItem.getSubtotal());
			total = total.add(subtotal);
		}
		return total.doubleValue();
	}
	
	//添加条目到购物车
	public void add(CartItem cartItem) {
		if(map.containsKey(cartItem.getBook().getBid())) {//判断原来车中是否存在该条目
			CartItem _cartItem = map.get(cartItem.getBook().getBid());//返回原条目
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());//设置老条目的数量
			map.put(cartItem.getBook().getBid(), _cartItem);
		}else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}		
	}
	
	//清空购物车
	public void clear() {
		map.clear();
	}
	
	//删除车中指定条目
	public void delete(String bid) {
		map.remove(bid);
	}
	
	//我的购物车
	public Collection<CartItem> getCartItem() {
		return map.values();
	}
	
}
