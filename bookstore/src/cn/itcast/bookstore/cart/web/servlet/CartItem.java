package cn.itcast.bookstore.cart.web.servlet;

import java.math.BigDecimal;

import cn.itcast.bookstore.book.domain.Book;

/**
 * 购物车条目类
 * @author lenovo
 *
 */
public class CartItem {
	private Book book;
	private int count;
	
	//小计
	public double getSubtotal() {//小计方法，但他没有对应的成员
		BigDecimal d1 = new BigDecimal(book.getPrice() + "");
		BigDecimal d2 = new BigDecimal(count + "");
		return d1.multiply(d2).doubleValue();
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
