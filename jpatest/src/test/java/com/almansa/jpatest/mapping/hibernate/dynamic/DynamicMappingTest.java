package com.almansa.jpatest.mapping.hibernate.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
		EmployeeWithIncome employee = new EmployeeWithIncome();
		employee.setName("NaYunsu");
		employee.setGrossIncome(300000);
		employee.setTaxInPercents(10);
		employee.setDeleted(false);

		entityManager.persist(employee);
		entityManager.flush();
		entityManager.clear();

		EmployeeWithIncome employeeGet = entityManager.find(EmployeeWithIncome.class, employee.getId());
		long tax = employeeGet.getTax();
		assertEquals(30000, tax);
	}

	@Test
	public void where테스트() {
		EmployeeWithIncome employee = new EmployeeWithIncome();
		employee.setName("NaYunsu");
		employee.setGrossIncome(300000);
		employee.setTaxInPercents(10);
		employee.setDeleted(true);

		entityManager.persist(employee);
		entityManager.flush();
		entityManager.clear();

		EmployeeWithIncome employeeGet = entityManager.find(EmployeeWithIncome.class, employee.getId());

		assertEquals(null, employeeGet);
	}

	@Test
	public void where테스트_jpql로_가져온다면() {
		EmployeeWithIncome employee = new EmployeeWithIncome();
		employee.setName("NaYunsu");
		employee.setGrossIncome(300000);
		employee.setTaxInPercents(10);
		employee.setDeleted(true);

		entityManager.persist(employee);
		entityManager.flush();
		entityManager.clear();

		TypedQuery<EmployeeWithIncome> query = entityManager.createQuery("select a from Employee a where is_deleted = :del",
				EmployeeWithIncome.class);
		query.setParameter("del", true);

		List<EmployeeWithIncome> resultList = query.getResultList();

		// 엔티티의 @Where어노테이션은 무시되지 않는다.
		// Native 쿼리를 쓰는 방법말고는 없는듯하다.
		assertEquals(0, resultList.size());
	}
}
