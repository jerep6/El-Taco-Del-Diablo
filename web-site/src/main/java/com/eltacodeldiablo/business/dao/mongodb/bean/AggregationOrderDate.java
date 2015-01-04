package com.eltacodeldiablo.business.dao.mongodb.bean;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Getter
@Setter
public class AggregationOrderDate {
	@Id
	private Map<String, Object>	id;
	private Integer				count;

	@Property("myDate")
	private Date				date;

}
