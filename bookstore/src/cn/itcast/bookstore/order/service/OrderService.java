package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 添加订单
	 * 需要处理事务
	 * @param order
	 */
	public void add(Order order) {
		try{
			//开启事务
			JdbcUtils.beginTransaction();
			
			orderDao.addOrder(order);	//插入订单
			orderDao.addOrderItemList(order.getOrderItemList());//插入订单条目
			//提交事务
			JdbcUtils.commitTransaction();
			//回滚事务
		}catch(Exception e) {
			try {
				JdbcUtils.rollbackTransaction();
			}catch(SQLException e1){
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 我的订单
	 * @param uid
	 * @return
	 */
	public List<Order> myOrdes(String uid) {
		return orderDao.findByUid(uid);
	}
	
	/**
	 * 加载订单
	 * @param attribute
	 * @return
	 */
	public Order load(String oid) {
		return orderDao.load(oid);
	}
	
	/**
	 * 确认收货
	 * @param oid
	 * @return
	 * @throws OrderException
	 */
	public void confirm(String oid) throws OrderException {
		/*
		 * 1.校验订单状态，如果不是3，抛出异常
		 */
		int state = orderDao.getStateByOid(oid);
		if(state != 3) throw new OrderException("订单确认失败，您不是什么好人！");
		/*
		 * 修改订单状态为4，表示交易成功
		 */
		orderDao.updateState(oid,4);
	}
	
	/**
	 * 支付
	 * @param oid
	 */
	public void zhiFu(String oid) {
		/*
		 * 1.获取订单状态
		 * 	如果为1，那么执行下面代码
		 * 	如果状态不为1，那么本方法什么都不做
		 */
		int state = orderDao.getStateByOid(oid);
		if(state == 1) {
			//修改订单状态
			orderDao.updateState(oid, 2);
		}
		
	}

	public List<Order> findAll() {
		return orderDao.findAll();
	}

	public void confirmSend(String oid) throws OrderException {
		/*
		 * 1.校验订单状态，如果不是2，抛出异常
		 */
		int state = orderDao.getStateByOid(oid);
		if(state != 2) throw new OrderException("发货失败！");
		/*
		 * 修改订单状态为3，表示发货成功
		 */
		orderDao.updateState(oid,3);
		
	}
}
