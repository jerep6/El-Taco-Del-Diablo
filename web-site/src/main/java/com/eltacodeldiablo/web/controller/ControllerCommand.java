package com.eltacodeldiablo.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceProduct;
import com.eltacodeldiablo.web.form.OrderForm;

@Controller
public class ControllerCommand {

	@Autowired
	private ServiceProduct	serviceProduct;

	@RequestMapping("/")
	public String index(Model model) {
		Map<ProductType, List<Product>> products = serviceProduct.list(new Date());

		// Extract all types (entrée, plat, dessert)
		Map<String, ProductType> types = new HashMap<>(products.size());
		for (ProductType aType : products.keySet()) {
			types.put(aType.getCode(), aType);
		}

		model.addAttribute("types", types);
		model.addAttribute("products", products);
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("order", new OrderForm());

		Map<String, String> tplMiddle = new HashMap<>();
		tplMiddle.put("html", "fragments/create_commande");
		tplMiddle.put("frg", "create_commande");
		model.addAttribute("tpl_middle", tplMiddle);
		return "index";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String order(@ModelAttribute OrderForm order, Model model) {
		serviceProduct.order(order);
		return "redirect:/";
	}

	@ModelAttribute
	public void populateModel() {

	}
}
