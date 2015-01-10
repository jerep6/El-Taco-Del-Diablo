package com.eltacodeldiablo.business.service;

import java.util.Date;
import java.util.List;

import com.eltacodeldiablo.business.dao.mongodb.bean.AggregationOrderDate;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.web.form.OrderForm;

public interface ServiceOrder {

	/**
	 * List order from a date
	 *
	 * @param date
	 * @return
	 */
	List<Order> list(Date date);

	void order(OrderForm order);

	List<AggregationOrderDate> getOrderDate();

	String generateQRCode(List<Order> orders);

}
