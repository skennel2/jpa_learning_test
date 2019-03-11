package com.almansa.jpatest.mapping.hibernate.id;

import static org.junit.Assert.assertEquals;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.StringUtils;

import com.almansa.jpatest.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class CustomIdGeneratorTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void Id생성전략을_custom한_엔티티_저장() {
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		
		entityManager.persist(employee);
		
		assertEquals(true, StringUtils.startsWithIgnoreCase(employee.getId(), "pk_"));
		assertEquals(true, Objects.nonNull(employee.getId()));
	}
}
