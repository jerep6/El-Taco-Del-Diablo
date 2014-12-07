package com.eltacodeldiablo.business.service;

import java.util.Date;
import java.util.List;

import com.eltacodeldiablo.business.domain.Product;

public interface ServiceProduct {

	List<Product> list(Date date);

}
