package com.eltacodeldiablo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.eltacodeldiablo.business.dao.mongodb.ConverterPrice;
import com.eltacodeldiablo.business.domain.Product;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {
	@Autowired
	Environment	env;

	@Bean
	public Datastore datastore() throws Exception {
		Morphia morphia = new Morphia();
		morphia.getMapper().getConverters().addConverter(new ConverterPrice());
		morphia.map(Product.class);
		Datastore ds = morphia.createDatastore(mongo(), "eltacodeldiablo");
		return ds;
	}

	@Bean
	public MongoClient mongo() throws Exception {
		return new MongoClient(env.getProperty("mongodb.url"));
	}

}
