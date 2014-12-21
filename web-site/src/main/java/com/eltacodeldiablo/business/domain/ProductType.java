package com.eltacodeldiablo.business.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
@ToString
public class ProductType {
	private String	code;
}
