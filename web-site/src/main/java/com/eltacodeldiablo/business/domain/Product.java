package com.eltacodeldiablo.business.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Getter
@Setter
@ToString(of = "name")
@Entity("product")
public class Product {
	@Id
	private String			id;
	private String			name;
	private Price			price;

	private List<String>	ingredients;
	private List<String>	spices;

	private ProductType		type;

}
