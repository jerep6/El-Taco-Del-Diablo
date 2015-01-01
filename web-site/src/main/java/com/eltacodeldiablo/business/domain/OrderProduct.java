package com.eltacodeldiablo.business.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Product in a client order. One ingredient and one spice
 *
 * @author jerep6
 */
@Getter
@Setter
@ToString(of = "name")
public class OrderProduct {
	private String		name;
	private Price		price;

	private String		ingredient;
	private String		spice;

	@Embedded
	private ProductType	type;

}
