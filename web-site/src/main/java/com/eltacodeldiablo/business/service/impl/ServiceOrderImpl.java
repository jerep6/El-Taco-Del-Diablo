package com.eltacodeldiablo.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.dao.mongodb.bean.AggregationOrderDate;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.business.domain.OrderProduct;
import com.eltacodeldiablo.business.domain.Price;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.service.ServiceOrder;
import com.eltacodeldiablo.utils.DateUtils;
import com.eltacodeldiablo.web.form.OrderForm;
import com.google.common.base.Preconditions;

@Service
public class ServiceOrderImpl implements ServiceOrder {
	private final Logger	LOGGER	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DaoProduct		daoProduct;

	@Autowired
	private DaoOrder		daoOrder;

	@Override
	public List<AggregationOrderDate> getOrderDate() {
		return daoOrder.getOrderDate();
	}

	@Override
	public List<Order> list(Date date) {
		Date boudMin = DateUtils.midnight(date);
		Date boudMax = DateUtils.addDay(date, 1);
		LOGGER.debug("Search order between {} and {}", boudMin, boudMax);

		return daoOrder.listFromDate(boudMin, boudMax);
	}

	private List<OrderProduct> mapOrderProduct(List<String> l) {
		Preconditions.checkNotNull(l);

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

		myOrder.setTotal(myOrder.getProducts().stream().map(p -> p.getPrice()).reduce(Price.ZERO, Price::add));
		daoOrder.create(myOrder);
	}
}
