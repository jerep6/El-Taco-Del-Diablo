package com.eltacodeldiablo.business.service;

import java.util.Date;
import java.util.List;

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

}
