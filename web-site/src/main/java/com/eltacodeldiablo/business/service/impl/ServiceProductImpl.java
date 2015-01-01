package com.eltacodeldiablo.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.business.domain.OrderProduct;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceProduct;
import com.eltacodeldiablo.web.form.OrderForm;

@Service
public class ServiceProductImpl implements ServiceProduct {

	@Autowired
	private DaoProduct	daoProduct;
	@Autowired
	private DaoOrder	daoOrder;

	@Override
	public Map<ProductType, List<Product>> list(Date date) {
		List<Product> products = daoProduct.listAll();

		Map<ProductType, List<Product>> collect = products.stream().collect(Collectors.groupingBy(p -> p.getType()));
		return collect;
	}

	private List<OrderProduct> mapOrderProduct(List<String> l) {
		List<OrderProduct> products = new ArrayList<OrderProduct>(l.size());

		for (String s : l.stream().filter(o -> !Strings.isEmpty(o)).collect(Collectors.toList())) {
			// Extract data to form
			String[] split = s.split("__");
			String productId = split[0];
			String ingr = split[1];

			// read product according to its id
			Product p = daoProduct.read(productId);

			// If ingredient is not found into product => exception
			Optional<String> ingrFind = p.getIngredients().stream().filter(i -> i.equals(ingr)).findFirst();
			ingrFind.orElseThrow(() -> new RuntimeException("Pouet"));

			// create order product
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setName(p.getName());
			orderProduct.setIngredient(ingrFind.get());
			orderProduct.setPrice(p.getPrice());
			orderProduct.setType(p.getType());
			products.add(orderProduct);
		}
		return products;
	}

	@Override
	public void order(OrderForm order) {
		Order myOrder = new Order();
		myOrder.setName(order.getName());
		myOrder.setDate(new Date());

		myOrder.addProducts(mapOrderProduct(order.getEntrees()));
		myOrder.addProducts(mapOrderProduct(order.getPlats()));
		myOrder.addProducts(mapOrderProduct(order.getDesserts()));

		daoOrder.create(myOrder);
	}
}
