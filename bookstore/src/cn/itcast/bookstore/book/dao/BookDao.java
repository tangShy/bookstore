package cn.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		try {
			String sql = "select * from book where del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按分类查询图书
	 * @param bid
	 * @return
	 */
	public List<Book> findByCategory(String cid) {
		try {
			String sql = "select * from book where cid=? and del=false";
			return qr.query(sql,new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	/**
	 * 查看图书详情（加载图书）
	 * @param bid
	 * @return
	 */
	public Book findByBid(String bid) {
		try {
			//我们需要在book对象中保存category信息
			String sql = "select * from book where bid=?";
			Map<String,Object> map = qr.query(sql, new MapHandler(),bid);
			//使用一个map映射出两个对象
			Category category = CommonUtils.toBean(map, Category.class);
			Book book = CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询指定分类下的图书本数
	 * @param cid
	 * @return
	 */
	public int getCountByCid(String cid) {
		try {
			String sql = "select count(*) from book where cid=?";
			Number cnt = (Number)qr.query(sql, new ScalarHandler(),cid);
			return cnt.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加图书
	 * @param book
	 */
	public void add(Book book) {
		try {
			String sql = "insert into book values(?,?,?,?,?,?,?)";
			Object[] params = {book.getBid(),book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCategory().getCid(),0};
			qr.update(sql,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除图书
	 * @param book
	 */
	public void delete(String bid) {
		try {
			String sql = "update book set del=true where bid=?";
			qr.update(sql,bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改图书
	 * @param book
	 */
	public void edit(Book book) {
		try {
			String sql = "update book set bname=?,price=?,author=?,image=?,cid=? where bid=?";
			Object[] params = {book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.getBid()};
			qr.update(sql,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
