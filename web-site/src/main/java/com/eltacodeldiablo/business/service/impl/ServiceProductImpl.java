package com.eltacodeldiablo.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eltacodeldiablo.business.dao.DaoProduct;
import com.eltacodeldiablo.business.domain.Image;
import com.eltacodeldiablo.business.domain.Price;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.service.ServiceProduct;

@Service
public class ServiceProductImpl implements ServiceProduct {

	@Autowired
	private DaoProduct	daoProduct;

	@Override
	public List<Product> list(Date date) {

		daoProduct.listAll();

		Product p1 = new Product();
		p1.setId("p1");
		p1.setName("Burrito");
		p1.setImage(new Image("Burito", "Burrito qui d�chire",
				"http://www.endlesssimmer.com/wp-content/uploads/2013/02/cantina_steak_burrito.jpg"));
		p1.setPrice(new Price(7F));
		Product p2 = new Product();
		p2.setId("p2");
		p2.setName("Guacamole and chips");
		p2.setImage(new Image("Guagamole", "POuet",
				"http://www.livraisonaperitifadomicile.com/aperojet7nice/wp-content/uploads/2013/12/guacamole.jpg"));
		p2.setPrice(new Price(4F));

		List<Product> products = new ArrayList<>();
		products.add(p1);
		products.add(p2);
		return products;
	}
}
