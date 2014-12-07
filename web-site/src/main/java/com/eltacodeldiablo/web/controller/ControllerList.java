package com.eltacodeldiablo.web.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eltacodeldiablo.business.service.ServiceProduct;

@Controller
public class ControllerList {

	@Autowired
	private ServiceProduct	serviceProduct;

	@RequestMapping("/list")
	public String contacts(Model model) {
		model.addAttribute("products", serviceProduct.list(new Date()));
		model.addAttribute("today", Calendar.getInstance());
		return "products_list";
	}
}
