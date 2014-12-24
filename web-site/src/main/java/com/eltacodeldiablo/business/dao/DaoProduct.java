package com.eltacodeldiablo.business.dao;

import java.util.List;

import com.eltacodeldiablo.business.domain.Product;

public interface DaoProduct {

	List<Product> listAll();

	Product read(String productId);
}
