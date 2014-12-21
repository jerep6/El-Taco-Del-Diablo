package com.eltacodeldiablo.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceProduct;

@Service
public class ServiceProductImpl implements ServiceProduct {

	@Autowired
	private DaoProduct	daoProduct;

	@Override
	public Map<ProductType, List<Product>> list(Date date) {
		List<Product> products = daoProduct.listAll();

		Map<ProductType, List<Product>> collect = products.stream().collect(Collectors.groupingBy(p -> p.getType()));
		return collect;
	}
}
