package com.almansa.jpatest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.almansa.jpatest.testentity.Employee;

// 이 어노테이션이 없으면 EntityManager가 로드되지 않는다.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class EntityTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void entityManagerLoadTest() {
		assertEquals(true, Objects.nonNull(entityManager));
	}

	@Test
	public void 엔티티저장하기() {
		Employee employee = new Employee();
		employee.setFirstName("Na");
		employee.setLastName("Yunsu");

		assertEquals(true, Objects.isNull(employee.getId()));
		entityManager.persist(employee);

		// @GenerateValue 어노테이션으로 인해 영속상태로 전환된 엔티티의 Id값이 채워졌다.
		assertEquals(true, Objects.nonNull(employee.getId()));
	}

	@Test(expected = PersistenceException.class)
	public void 데이터베이스_컬럼길이_초과_엔티티저장() {
		String overSizeFirstName = "over size first name";

		Employee employee = new Employee();
		employee.setFirstName(overSizeFirstName);
		employee.setLastName("Yunsu");

		entityManager.persist(employee);
	}

	@Test
	public void JPQL로_하나의_엔티티_가져오기() {
		Employee employee = new Employee();
		employee.setFirstName("Na");
		employee.setLastName("Yunsu");

		entityManager.persist(employee);

		TypedQuery<Employee> query = entityManager.createQuery("SELECT a FROM Employee a Where a.lastName = :lastName",
				Employee.class);

		query.setParameter("lastName", "Yunsu");
		Employee employeeGet = query.getSingleResult(); // 만약 데이터가 여러건이 조회되었다면?

		assertEquals("Na", employeeGet.getFirstName());
	}

	@Test(expected = NonUniqueResultException.class)
	public void getSingleResult의_결과가_여러건이라면() {
		Employee employee = new Employee();
		employee.setFirstName("Na");
		employee.setLastName("Yunsu");
		entityManager.persist(employee);

		Employee employee2 = new Employee();
		employee2.setFirstName("Na");
		employee2.setLastName("Jinsu");
		entityManager.persist(employee2);

		TypedQuery<Employee> query = entityManager
				.createQuery("SELECT a FROM Employee a Where a.firstName = :firstName", Employee.class);
		query.setParameter("firstName", "Na");

		// 실제 데이터베이스에서 가져온 것은 2개지만 getSingleResult 호출.
		Employee singleResult = query.getSingleResult();
	}
	
	@Test
	public void 하나의_TypedQuery로_여러번의_결과리턴_메소드호출() {
		Employee employee = new Employee();
		employee.setFirstName("Na");
		employee.setLastName("Yunsu");
		entityManager.persist(employee);

		Employee employee2 = new Employee();
		employee2.setFirstName("Na");
		employee2.setLastName("Jinsu");
		entityManager.persist(employee2);

		TypedQuery<Employee> query = entityManager
				.createQuery("SELECT a FROM Employee a Where a.firstName = :firstName", Employee.class);
		query.setParameter("firstName", "Na");

		List<Employee> resultList1 = query.getResultList();
		assertEquals(2, resultList1.size());
		
		// 하나의 Query 인스턴스로 결과리턴 메소드를 여러번 호출하여도 문제없다.
		List<Employee> resultList = query.getResultList();
		assertEquals(2, resultList.size());
	}
}
