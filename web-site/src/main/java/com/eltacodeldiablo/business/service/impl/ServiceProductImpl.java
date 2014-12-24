package com.eltacodeldiablo.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceProduct;
import com.eltacodeldiablo.web.form.OrderForm;

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

	@Override
	public void order(OrderForm order) {
		for (String aPlat : order.getPlats().stream().filter(o -> !Strings.isEmpty(o)).collect(Collectors.toList())) {
			// Extract data to form
			String[] split = aPlat.split("__");
			String productId = split[0];
			String ingr = split[1];

			// read product according to its identifiant
			Product p = daoProduct.read(productId);

			System.out.println(p);

		}
	}
}
