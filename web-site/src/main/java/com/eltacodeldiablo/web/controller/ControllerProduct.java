package com.eltacodeldiablo.web.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eltacodeldiablo.business.service.ServiceProduct;

@Controller
public class ControllerProduct {

	@Autowired
	private ServiceProduct	serviceProduct;

	@RequestMapping("/product/list")
	public String contacts(Model model) {
		model.addAttribute("products", serviceProduct.list(new Date()));
		model.addAttribute("today", Calendar.getInstance());
		return "product/products_list";
	}

	@RequestMapping("/product/details")
	public String details(@RequestParam("id") String id, Model model) {
		System.out.println(id);
		return "product/products_detail";
	}

	@ModelAttribute
	public void populateModel() {

	}
}
