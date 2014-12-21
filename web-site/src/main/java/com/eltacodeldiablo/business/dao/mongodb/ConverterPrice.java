package com.eltacodeldiablo.business.dao.mongodb;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import com.eltacodeldiablo.business.domain.Price;

public class ConverterPrice extends TypeConverter implements SimpleValueConverter {

	public ConverterPrice() {
		super(Price.class);
	}

	@Override
	public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo)
			throws MappingException {
		if (fromDBObject != null) {
			return new Price(Float.valueOf(fromDBObject.toString()));
		}
		return null;
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		if (value instanceof Price) {
			return ((Price) value).getValue();
		}
		return null;
	}
}
