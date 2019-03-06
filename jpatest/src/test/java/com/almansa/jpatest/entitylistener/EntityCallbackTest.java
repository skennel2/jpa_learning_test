package com.almansa.jpatest.entitylistener;

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
public class EntityCallbackTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void 콜백메소드를_가진_엔티티저장() {
		StudentWithCallback student = new StudentWithCallback();
		student.setFirstName("Na");
		student.setLastName("Yunsu");

		entityManager.persist(student);

		student.setLastName("Jinsu");				

		entityManager.flush();
		
		entityManager.remove(student);
	}
	
	@Test
	public void 콜백리스너클래스를_지정한_엔티티저장() {
		StudentWithEntityLister student = new StudentWithEntityLister();
		student.setFirstName("Na");
		student.setLastName("Yunsu");

		entityManager.persist(student);

		student.setLastName("Jinsu");				

		entityManager.flush();
		
		entityManager.remove(student);
	}
}
