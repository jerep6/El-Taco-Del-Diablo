package com.eltacodeldiablo.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;

public interface ServiceProduct {

	Map<ProductType, List<Product>> list(Date date);

}
