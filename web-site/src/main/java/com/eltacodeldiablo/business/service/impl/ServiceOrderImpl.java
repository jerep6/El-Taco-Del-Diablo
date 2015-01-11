package com.eltacodeldiablo.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.eltacodeldiablo.business.dao.DaoOrder;
import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.dao.mongodb.bean.AggregationOrderDate;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.business.domain.OrderProduct;
import com.eltacodeldiablo.business.domain.Price;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceOrder;
import com.eltacodeldiablo.utils.DateUtils;
import com.eltacodeldiablo.web.form.OrderForm;
import com.eltacodeldiablo.web.form.SMS;
import com.google.common.base.Preconditions;

@Service
public class ServiceOrderImpl implements ServiceOrder {
	private final Logger	LOGGER	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DaoProduct		daoProduct;

	@Autowired
	private DaoOrder		daoOrder;

	@Override
	public SMS generateSMS(List<Order> orders) {
		StringBuilder builderSmsOrder = new StringBuilder();
		// Product by type
		Map<ProductType, List<OrderProduct>> productsByType = orders.stream().map(Order::getProducts)
				.flatMap(Collection::stream).collect(Collectors.groupingBy(OrderProduct::getType));

		// For each type (entrée, plat, dessert) generate string
		// nombre + label
		for (Entry<ProductType, List<OrderProduct>> e : productsByType.entrySet()) {

			// Compute quantity for each product
			Map<OrderProduct, Long> productsQte = e.getValue().stream()
					.collect(Collectors.groupingBy(op -> op, Collectors.counting()));

			for (Entry<OrderProduct, Long> order : productsQte.entrySet()) {
				OrderProduct product = order.getKey();
				Long totalOrder = order.getValue();

				builderSmsOrder.append(totalOrder).append(" ");
				builderSmsOrder.append(product.getName());
				if (!Strings.isEmpty(product.getIngredient())) {
					builderSmsOrder.append(" " + product.getIngredient());
				}
				if (!Strings.isEmpty(product.getSpice())) {
					builderSmsOrder.append(" " + product.getSpice());
				}
				builderSmsOrder.append("\n");

			}
		}
		return new SMS(builderSmsOrder.toString(), generateQRCode(builderSmsOrder.toString()));
	}

	@Override
	public String generateQRCode(String txt) {
		if (com.google.common.base.Strings.isNullOrEmpty(txt)) {
			return "";
		}
		byte[] img = Base64Utils.encode(QRCode.from(txt).withCharset("UTF-8").to(ImageType.PNG).withSize(250, 250)
				.stream().toByteArray());
		return new String(img);
	}

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
			String ingr = split.length > 1 ? split[1] : "";

			// read product according to its id
			Product p = daoProduct.read(productId);

			// create order product
			OrderProduct orderProduct = new OrderProduct();

			// Some products don't have ingredient (desserts for example)
			if (!Strings.isEmpty(ingr)) {
				// If ingredient is not found into product => exception
				Optional<String> ingrFind = p.getIngredients().stream().filter(i -> i.equals(ingr)).findFirst();
				ingrFind.orElseThrow(() -> new RuntimeException("Pouet"));
				orderProduct.setIngredient(ingrFind.get());
			}

			orderProduct.setName(p.getName());
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
