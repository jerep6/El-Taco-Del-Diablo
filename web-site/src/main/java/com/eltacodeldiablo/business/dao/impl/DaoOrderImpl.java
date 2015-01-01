package com.eltacodeldiablo.business.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.utils.DateUtils;
import com.mongodb.MongoClient;

@Repository
public class DaoOrderImpl extends BasicDAO<Order, String> implements DaoOrder {
	private final Logger	LOGGER	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	public DaoOrderImpl(MongoClient mongoClient, Morphia morphia, Environment env) {
		super(mongoClient, morphia, env.getProperty("mongodb.database"));
	}

	@Override
	public void create(Order o) {
		Key<Order> save = super.save(o);
		System.out.println(save);
	}

	@Override
	public List<Order> listFromDate(Date date) {
		Date boudMin = DateUtils.midnight(date);
		Calendar cboudMax = Calendar.getInstance();
		cboudMax.add(Calendar.DAY_OF_YEAR, 1);
		Date boudMax = DateUtils.midnight(cboudMax.getTime());

		LOGGER.debug("Search order between {} and {}", boudMin, boudMax);

		List<Order> orders = getDatastore().createQuery(Order.class)//
				.filter("date >=", boudMin)//
				.filter("date <", boudMax)//
				.asList();
		return orders;
	}

}
