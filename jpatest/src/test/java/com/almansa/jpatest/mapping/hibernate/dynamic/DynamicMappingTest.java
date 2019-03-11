package com.almansa.jpatest.mapping.hibernate.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class DynamicMappingTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void fomula테스트() {
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setGrossIncome(300000);
		employee.setTaxInPercents(10);
		employee.setDeleted(false);

		entityManager.persist(employee);
		entityManager.flush();
		entityManager.clear();

		Employee employeeGet = entityManager.find(Employee.class, employee.getId());
		long tax = employeeGet.getTax();
		assertEquals(30000, tax);
	}

	@Test
	public void where테스트() {
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setGrossIncome(300000);
		employee.setTaxInPercents(10);
		employee.setDeleted(true);

		entityManager.persist(employee);
		entityManager.flush();
		entityManager.clear();

		Employee employeeGet = entityManager.find(Employee.class, employee.getId());

		assertEquals(null, employeeGet);
	}
}
