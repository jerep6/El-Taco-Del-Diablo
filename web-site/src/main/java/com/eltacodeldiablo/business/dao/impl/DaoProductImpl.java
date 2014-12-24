package com.eltacodeldiablo.business.dao.impl;

import java.util.List;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.mongodb.MongoClient;

@Repository
public class DaoProductImpl extends BasicDAO<Product, String> implements DaoProduct {

	@Autowired
	public DaoProductImpl(MongoClient mongoClient, Morphia morphia, Environment env) {
		super(mongoClient, morphia, env.getProperty("mongodb.database"));
	}

	@Override
	public List<Product> listAll() {
		List<Product> products = find().asList();
		return products;

		// DB database = mongoClient.getDB("eltacodeldiablo");
		// DBCollection collection = database.getCollection("product");
		//
		// DBCursor cursor = collection.find();
		// for (DBObject dbObject : cursor) {
		// System.out.println(dbObject);
		// }
	}

	@Override
	public Product read(String productId) {
		return get(productId);
	}
}
