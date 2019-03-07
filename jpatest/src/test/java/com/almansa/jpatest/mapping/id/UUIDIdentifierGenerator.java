package com.almansa.jpatest.mapping.id;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class UUIDIdentifierGenerator implements IdentifierGenerator, Configurable {

	private String prefix;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		// Id필드에서 @GenericGenerator를 통해 파라미터를 넘길 수 있다.
		prefix = params.getProperty("prefix");
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return prefix + "-" + UUID.randomUUID().toString();
	}

}
