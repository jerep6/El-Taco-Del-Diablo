package com.eltacodeldiablo.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.web.form.OrderForm;

public interface ServiceProduct {

	Map<ProductType, List<Product>> list(Date date);

	void order(OrderForm order);

}
