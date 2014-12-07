package com.eltacodeldiablo.business.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Price {
	private Float	value;

	public Price(Float price) {
		super();
		this.value = price;
	}

}
