package com.almansa.jpatest.mapping.converter;

import static org.junit.Assert.assertEquals;

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
public class ConverterTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void 컨버트필드가_있는_엔티티저장후_가져오기테스트() {
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setEmailAddress(new EmailAddress("skennel2@gmail.com"));
		entityManager.persist(employee);

		// TODO Insert 구문 이후에 Update구문이 날아간다?
		entityManager.flush();
		entityManager.clear();

		Employee employeeGet = entityManager.find(Employee.class, employee.getId());
		assertEquals("skennel2@gmail.com", employeeGet.getEmailAddress().getEmailStringValue());
	}
}
