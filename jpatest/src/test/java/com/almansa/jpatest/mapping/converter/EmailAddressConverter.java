package com.almansa.jpatest.mapping.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmailAddressConverter implements AttributeConverter<EmailAddress, String> {

	@Override
	public String convertToDatabaseColumn(EmailAddress attribute) {
		if (Objects.isNull(attribute)) {
			return null;
		}
		return attribute.getEmailStringValue();
	}

	@Override
	public EmailAddress convertToEntityAttribute(String dbData) {
		if (Objects.isNull(dbData)) {
			return null;
		}
		return new EmailAddress(dbData);
	}
}