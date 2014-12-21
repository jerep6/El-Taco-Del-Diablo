package com.eltacodeldiablo.business.dao.impl;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.dao.mongodb.ConverterPrice;
import com.eltacodeldiablo.business.domain.Product;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Repository
public class DaoProductImpl implements DaoProduct {

	@Autowired
	private MongoClient	mongoClient;

	@Override
	public List<Product> listAll() {

		Morphia morphia = new Morphia();
		morphia.getMapper().getConverters().addConverter(new ConverterPrice());
		morphia.map(Product.class);
		Datastore ds = morphia.createDatastore(mongoClient, "eltacodeldiablo");

		List<Product> products = ds.find(Product.class).asList();
		System.out.println(products);

		DB database = mongoClient.getDB("eltacodeldiablo");
		DBCollection collection = database.getCollection("product");

		DBCursor cursor = collection.find();
		for (DBObject dbObject : cursor) {
			System.out.println(dbObject);
		}
		return null;
	}

}
