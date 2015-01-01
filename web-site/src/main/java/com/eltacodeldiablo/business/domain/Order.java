package com.eltacodeldiablo.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Setter
@Getter
@ToString(of = "name")
@Entity("order")
public class Order {
	@Id
	private String				id;
	private String				name;
	private Date				date;

	@Embedded
	private List<OrderProduct>	products	= new ArrayList<>(0);	;
	private Price				approximativePrice;

	public void addProducts(List<OrderProduct> productsToAdd) {
		products.addAll(productsToAdd);
	}

}
