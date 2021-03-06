package com.eltacodeldiablo.business.dao.mongodb;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import com.eltacodeldiablo.business.domain.ProductType;
import com.mongodb.BasicDBObject;

public class ConverterProductType extends TypeConverter implements SimpleValueConverter {
	private final Morphia	morphia;

	public ConverterProductType(Morphia morphia) {
		super(ProductType.class);
		this.morphia = morphia;
	}

	@Override
	public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo)
			throws MappingException {
		if (fromDBObject != null && fromDBObject instanceof BasicDBObject) {
			return new ProductType(((BasicDBObject) fromDBObject).getString("code"));
		}
		return null;
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		return morphia.toDBObject(value);
	}
}
