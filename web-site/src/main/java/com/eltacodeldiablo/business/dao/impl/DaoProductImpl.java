package com.eltacodeldiablo.business.dao.impl;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.mongodb.MongoClient;

@Repository
public class DaoProductImpl implements DaoProduct {

	@Autowired
	private MongoClient	mongoClient;

	@Autowired
	private Datastore	datastore;

	@Override
	public List<Product> listAll() {
		List<Product> products = datastore.find(Product.class).asList();
		return products;

		// DB database = mongoClient.getDB("eltacodeldiablo");
		// DBCollection collection = database.getCollection("product");
		//
		// DBCursor cursor = collection.find();
		// for (DBObject dbObject : cursor) {
		// System.out.println(dbObject);
		// }
	}

}
