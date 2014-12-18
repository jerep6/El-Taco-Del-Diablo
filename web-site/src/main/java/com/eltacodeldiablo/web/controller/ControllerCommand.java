package com.eltacodeldiablo.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eltacodeldiablo.business.service.ServiceProduct;

@Controller
public class ControllerCommand {

	@Autowired
	private ServiceProduct	serviceProduct;

	@RequestMapping("/product/details")
	public String details(@RequestParam("id") String id, Model model) {
		System.out.println(id);
		return "product/products_detail";
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("products", serviceProduct.list(new Date()));
		model.addAttribute("today", Calendar.getInstance());

		Map<String, String> tplMiddle = new HashMap<>();
		tplMiddle.put("html", "fragments/create_commande");
		tplMiddle.put("frg", "create_commande");
		model.addAttribute("tpl_middle", tplMiddle);
		return "index";
	}

	@ModelAttribute
	public void populateModel() {

	}
}
