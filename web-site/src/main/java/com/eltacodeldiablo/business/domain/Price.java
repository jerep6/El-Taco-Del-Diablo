package com.eltacodeldiablo.business.domain;

import java.math.BigDecimal;

import lombok.ToString;

@ToString
public class Price {
	public static Price	ZERO	= new Price(0F);
	public static Price	ONE		= new Price(1F);
	private Float		value;

	public Price(Float price) {
		super();
		this.value = price;
	}

	public Price add(Price p) {
		return new Price(value + p.getRawValue());
	}

	public Float getRawValue() {
		return value;
	}

	public Float getValue() {
		BigDecimal bd = new BigDecimal(Float.toString(value));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public Price substract(Price p) {
		return new Price(value - p.getRawValue());
	}

}
