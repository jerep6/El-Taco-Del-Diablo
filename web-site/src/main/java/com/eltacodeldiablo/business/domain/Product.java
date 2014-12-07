package com.eltacodeldiablo.business.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private String	id;
	private String	name;
	private Price	price;
	private Image	image;
}
