package cn.itcast.bookstore.order.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.service.OrderException;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
	/**
	 * 查看所有订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Order> orderList = orderService.findAll();
		request.setAttribute("orderList", orderList);
		return "f:/adminjsps/admin/order/list.jsp";
	}	
	
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OrderException {
		/*
		 * 1.得到oid
		 * 2.使用oid调用service方法
		 */
		String oid = request.getParameter("oid");
		orderService.confirmSend(oid);
		System.out.println("oid====="+oid);
		request.setAttribute("msg", "恭喜您，发货成功，等待买家确认收货！");
		return "f:/adminjsps/admin/msg.jsp";
	}	
}
