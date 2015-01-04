package com.eltacodeldiablo.web.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDateCount {

	private Date	date;
	private Integer	count;
	private Boolean	active;

}
