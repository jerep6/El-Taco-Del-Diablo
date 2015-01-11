package com.eltacodeldiablo.web.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SMS {

	private String	text;
	private String	qrcode;

	public SMS() {
		super();
	}
}
