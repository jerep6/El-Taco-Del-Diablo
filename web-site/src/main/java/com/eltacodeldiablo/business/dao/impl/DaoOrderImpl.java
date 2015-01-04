package com.eltacodeldiablo.business.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.dao.mongodb.bean.AggregationOrderDate;
import com.eltacodeldiablo.business.domain.Order;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Repository
public class DaoOrderImpl extends BasicDAO<Order, String> implements DaoOrder {

	@Autowired
	private Morphia	morphia;

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
	public List<AggregationOrderDate> getOrderDate() {

		// Match : date field must exist
		DBObject match = new BasicDBObject("$match", new BasicDBObject("date", new BasicDBObject("$exists", true)));

		// Group by day and count order
		DBObject id = new BasicDBObject();
		id.put("day", new BasicDBObject("$dayOfMonth", "$date"));
		id.put("month", new BasicDBObject("$month", "$date"));
		id.put("year", new BasicDBObject("$year", "$date"));
		DBObject groupFields = new BasicDBObject("_id", id);
		groupFields.put("myDate", new BasicDBObject("$min", "$date"));
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		List<DBObject> pipeline = Arrays.asList(match, group);
		AggregationOutput aggregate = getDatastore().getCollection(Order.class).aggregate(pipeline);
		System.out.println(aggregate);

		List<AggregationOrderDate> agg = new ArrayList<AggregationOrderDate>();
		for (DBObject dbObject : aggregate.results()) {
			agg.add(morphia.fromDBObject(AggregationOrderDate.class, dbObject));
		}
		return agg;

		// Query<Object> queryMatch = getDatastore().getQueryFactory().createQuery(getDatastore());
		//
		// AggregationPipeline<Order, Object> group = getDatastore().createAggregation(Order.class)
		// .match(queryMatch.field("date").exists())
		// //
		// .group(Group.id(Group.grouping("month", new Accumulator("$month", "date")),
		// Group.grouping("year", new Accumulator("$year", "date")),
		// Group.grouping("day", new Accumulator("$dayOfMonth", "date"))), //
		// // Group.grouping("count", Group.sum("1")), //
		// Group.grouping("dateMin", Group.min("date"))
		//
		// );
		//
		// System.out.println(group);
		// MorphiaIterator<Object, Object> aggregate = group.aggregate(Object.class);
		// while (aggregate.hasNext()) {
		// System.out.println(aggregate.next());
		// }
	}

	@Override
	public List<Order> listFromDate(Date dateMin, Date dateMax) {

		List<Order> orders = getDatastore().createQuery(Order.class)//
				.filter("date >=", dateMin)//
				.filter("date <", dateMax)//
				.asList();
		return orders;
	}
}
