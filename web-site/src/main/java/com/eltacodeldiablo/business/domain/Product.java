package com.eltacodeldiablo.business.domain;

import lombok.Getter;
import lombok.Setter;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Getter
@Setter
@Entity("product")
public class Product {
	@Id
	private String	id;
	private String	name;
	private Price	price;
	private Image	image;
}
