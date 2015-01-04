package com.eltacodeldiablo.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.eltacodeldiablo.business.dao.mongodb.bean.AggregationOrderDate;
import com.eltacodeldiablo.business.domain.Order;
import com.eltacodeldiablo.business.domain.Product;
import com.eltacodeldiablo.business.domain.ProductType;
import com.eltacodeldiablo.business.service.ServiceOrder;
import com.eltacodeldiablo.business.service.ServiceProduct;
import com.eltacodeldiablo.utils.DateUtils;
import com.eltacodeldiablo.web.bean.OrderDateCount;
import com.eltacodeldiablo.web.form.OrderForm;

@Controller
public class ControllerCommand {

	@Autowired
	private ServiceProduct	serviceProduct;

	@Autowired
	private ServiceOrder	serviceOrder;

	private List<OrderDateCount> convert(Date selectedDate, List<AggregationOrderDate> orderDate) {
		List<OrderDateCount> l = new ArrayList<>(orderDate.size());
		for (AggregationOrderDate o : orderDate) {
			l.add(new OrderDateCount(o.getDate(), o.getCount(), DateUtils.equalsYMD(selectedDate, o.getDate())));
		}
		return l;
	}

	private Date getSearchOrderDate(String date) {
		Date dateToList;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
			dateToList = sd.parse(date);
		} catch (Exception e) {
			dateToList = new Date();
		}
		return dateToList;
	}

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
		tplMiddle.put("html", "fragments/order");
		tplMiddle.put("frg", "order_create");
		model.addAttribute("tpl_middle", tplMiddle);
		return "index";
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String listOrder(Model model, @RequestParam(value = "d", required = false) String date) {

		Date dateToList = getSearchOrderDate(date);
		List<Order> orders = serviceOrder.list(dateToList);
		List<AggregationOrderDate> orderDate = serviceOrder.getOrderDate();

		model.addAttribute("orders", orders);
		model.addAttribute("dates", convert(dateToList, orderDate));
		model.addAttribute("currentDate", dateToList);

		Map<String, String> tplMiddle = new HashMap<>();
		tplMiddle.put("html", "fragments/order");
		tplMiddle.put("frg", "order_list");
		model.addAttribute("tpl_middle", tplMiddle);
		return "index";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String order(@ModelAttribute OrderForm order, Model model) {
		serviceOrder.order(order);
		return "redirect:/";
	}

	@ModelAttribute
	public void populateModel() {

	}
}
