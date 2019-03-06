package com.almansa.jpatest.mapping.auditing;

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

import com.almansa.jpatest.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class AuditingTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void audition동작테스트() {
		NamedEntity entity = new NamedEntity();
		entity.setName("Named");
				
		entityManager.persist(entity);
		
		assertEquals(true, Objects.nonNull(entity.getId()));
		assertEquals(true, Objects.nonNull(entity.getCreatedDate()));
		assertEquals(true, Objects.nonNull(entity.getLastModifiedDate()));
	}
}
