package cn.itcast.bookstore.book.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao = new BookDao();
	
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		return bookDao.findAll();
	}
	
	/**
	 * 按分类查询图书
	 * @param bid
	 * @return
	 */
	public List<Book> findByCategory(String cid){
		return bookDao.findByCategory(cid);
	}
	
	/**
	 * 查看图书详情（加载图书）
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		return bookDao.findByBid(bid);
	}
	
	//添加图书
	public void add(Book book) {
		bookDao.add(book);
	}
	
	//删除图书
	public void delete(String bid) {
		bookDao.delete(bid);
	}

	//修改图书
	public void edit(Book book) {
		bookDao.edit(book);
	}
	
}
