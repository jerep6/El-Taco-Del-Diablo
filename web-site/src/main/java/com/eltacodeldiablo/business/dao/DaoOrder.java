package com.eltacodeldiablo.business.dao;

import java.util.Date;
import java.util.List;

import com.eltacodeldiablo.business.domain.Order;

public interface DaoOrder {

	void create(Order o);

	List<Order> listFromDate(Date date);

}
