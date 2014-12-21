package com.eltacodeldiablo.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.service.ServiceProduct;

@Service
public class ServiceProductImpl implements ServiceProduct {

	@Autowired
	private DaoProduct	daoProduct;

	@Override
	public List<Product> list(Date date) {
		List<Product> products = daoProduct.listAll();
		return products;
	}
}
