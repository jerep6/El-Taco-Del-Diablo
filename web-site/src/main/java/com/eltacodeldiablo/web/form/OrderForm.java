package com.eltacodeldiablo.web.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
	private String			name;
	private List<String>	plats;
	private List<String>	entrees;
	private List<String>	desserts;
}
