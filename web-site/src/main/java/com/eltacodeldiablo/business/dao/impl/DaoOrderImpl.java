package com.eltacodeldiablo.business.dao.impl;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.domain.Order;
import com.mongodb.MongoClient;

@Repository
public class DaoOrderImpl extends BasicDAO<Order, String> implements DaoOrder {

	@Autowired
	public DaoOrderImpl(MongoClient mongoClient, Morphia morphia, Environment env) {
		super(mongoClient, morphia, env.getProperty("mongodb.database"));
	}

	@Override
	public void create(Order o) {
		Key<Order> save = super.save(o);
		System.out.println(save);
	}

}
