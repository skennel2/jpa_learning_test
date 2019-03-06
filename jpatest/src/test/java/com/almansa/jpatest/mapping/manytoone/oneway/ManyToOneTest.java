package com.almansa.jpatest.mapping.manytoone.oneway;

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
public class ManyToOneTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void 다대일관계_엔티티_저장하기() {
		Department devDepartment = new Department();
		devDepartment.setName("software development");
		entityManager.persist(devDepartment);

		Employee employee = new Employee();
		employee.setDepartmentLazy(devDepartment);
		employee.setName("NaYunsu");
		entityManager.persist(employee);

		entityManager.flush();
	}

	@Test
	public void fetch타입이_lazy일때_특징() {
		// 부서영속화
		Department devDepartment = new Department();
		devDepartment.setName("software development");
		entityManager.persist(devDepartment);

		// 사원에 부서설정후 영속화
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setDepartmentLazy(devDepartment);
		entityManager.persist(employee);
		
		entityManager.flush();
		// 캐시가 아닌 db에서 데이터를 가져오는 것을 보기위해 clear시켰다.
		entityManager.clear();

		Department departmentGet = entityManager.find(Employee.class, employee.getId())
				.getDepartmentLazy();

		// 실제 사용하는 departmentGet.getName() 시점에서 Department를 조회해온다.
		assertEquals("software development", departmentGet.getName());
	}

	@Test
	public void 준영속엔티티의_lazy_fetch() {
		// 부서영속화
		Department devDepartment = new Department();
		devDepartment.setName("software development");
		entityManager.persist(devDepartment);

		// 사원에 부서설정후 영속화
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setDepartmentLazy(devDepartment);
		entityManager.persist(employee);

		entityManager.flush();
		entityManager.clear();

		Employee employeeGet = entityManager.find(Employee.class, employee.getId());

		entityManager.detach(employeeGet);

		// TODO 예외를 던질거 같은데 잘된다.
		Department departmentGet = employeeGet.getDepartmentLazy();
		assertEquals(true, entityManager.contains(departmentGet));
	}
	
	@Test
	public void fetch타입이_eager일때_특징() {
		// 부서 영속화
		Department devDepartment = new Department();
		devDepartment.setName("software development");
		entityManager.persist(devDepartment);

		// 사원에 부서설정후 영속화
		Employee employee = new Employee();
		employee.setName("NaYunsu");
		employee.setDepartmentEager(devDepartment);
		entityManager.persist(employee);
		
		entityManager.flush();
		entityManager.clear();
		
		// left outer join으로 모든 Department필드까지 함께 조회해서 가져온다.
		Employee employeeGet = entityManager.find(Employee.class, employee.getId());

		Department departmentGet = employeeGet.getDepartmentEager();
		assertEquals("software development", departmentGet.getName());
	}
}
