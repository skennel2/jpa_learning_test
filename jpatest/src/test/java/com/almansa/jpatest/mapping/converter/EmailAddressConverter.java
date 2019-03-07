package com.almansa.jpatest.mapping.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//autoApply = true 속성을 주면 모든 클로벌 설정으로 모든 EmailAddress에 동일한 컨버팅을 진행한다.
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