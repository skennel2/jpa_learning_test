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
public class EntityManagerTest {

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

		// persist로 객체를 영속상태로 만든다.해당 객체는 영속성 컨테스트에서 관리될 것이다.
		// 아래 시점에 시퀀스테이블에서 새로은 PK값을 조회해 엔티티에 채워넣는다.
		entityManager.persist(employee);
		assertEquals(true, Objects.nonNull(employee.getId()));

		// 실제 Insert구문이 날라간다.
		entityManager.flush();
	}

	@Test(expected = PersistenceException.class)
	public void 데이터베이스_컬럼길이_초과_엔티티저장() {
		String overSizeFirstName = "over size first name";

		Employee employee = new Employee();
		employee.setFirstName(overSizeFirstName);
		employee.setLastName("Yunsu");

		entityManager.persist(employee);

		// 아래구문을 제외하니까 예외가 발생하지 않는다.
		// persist()까지는 1차캐시에서 관리되고 실제 DB와 동기화되지 않는다.		
		entityManager.flush();
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

		// 실제 데이터베이스에서 가져온 것은 2개지만 getSingleResult 호출하니 NonUniqueResultException을 던진다.
		query.getSingleResult();
	}

	@Test
	public void 하나의_TypedQuery인스턴스로_여러번의_결과리턴_메소드호출() {
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

		// 하나의 Query 인스턴스로 결과리턴 메소드를 여러번 호출하여도 문제없으며 결과도 동일하다
		List<Employee> resultList = query.getResultList();
		assertEquals(2, resultList.size());
	}
	
	@Test
	public void paging기법_으로_데이터가져오기() {
		Employee employee = new Employee();
		employee.setFirstName("Na");
		employee.setLastName("Yunsu");
		entityManager.persist(employee);
		
		Employee employee2 = new Employee();
		employee2.setFirstName("Na");
		employee2.setLastName("Jinsu");
		entityManager.persist(employee2);		
		
		Employee employee3 = new Employee();
		employee3.setFirstName("Kim");
		employee3.setLastName("Yunmi");
		entityManager.persist(employee3);
		
		Employee employee4 = new Employee();
		employee4.setFirstName("Na");
		employee4.setLastName("Jangsu");
		entityManager.persist(employee4);		
		
		TypedQuery<Employee> query = entityManager
				.createQuery("SELECT a FROM Employee a Where a.firstName = :firstName", Employee.class)
				.setParameter("firstName", "Na")
				.setFirstResult(1)
				.setMaxResults(2);			
		List<Employee> employees = query.getResultList();
		
		assertEquals("Jinsu", employees.get(0).getLastName());
		assertEquals("Jangsu", employees.get(1).getLastName());
	}
}
