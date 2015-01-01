package com.eltacodeldiablo.business.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.mongodb.morphia.annotations.Embedded;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
@ToString
@Embedded
public class ProductType {
	private String	code;

	private ProductType() {
		super();
	}

}
