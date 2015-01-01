package com.eltacodeldiablo.web.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
	private String			name;
	/**
	 * String is formed as is : product_id
	 */
	private List<String>	plats		= new ArrayList<>(0);
	private List<String>	entrees		= new ArrayList<>(0);
	private List<String>	desserts	= new ArrayList<>(0);
}
