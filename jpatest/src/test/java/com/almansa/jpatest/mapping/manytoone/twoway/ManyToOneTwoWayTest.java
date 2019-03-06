package com.almansa.jpatest.mapping.manytoone.twoway;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
public class ManyToOneTwoWayTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void 양방향관계_엔티티() {
		// 부서 추가
		Department department = new Department();
		department.setName("RND");
		entityManager.persist(department);

		// 사원1 추가 부서 세팅
		Employee employee = new Employee();
		employee.setName("Nayunsu");
		employee.setDepartment(department);
		entityManager.persist(employee);

		// 사원2 추가 부서 세팅
		Employee employee2 = new Employee();
		employee2.setName("Najinsu");
		employee2.setDepartment(department);
		entityManager.persist(employee2);
		
		// 편의메소드로 오브젝트의 관점에서도 관계가 잘 설정되었다.
		assertEquals(2, department.getEmployees().size());
		
		// flush후 캐시 초기화
		entityManager.flush();
		entityManager.clear();
		
		Department departmentGet = entityManager.find(Department.class, department.getId());
		
		// 이 시점에 Employee 테이블을 조회한다. 
		// 정상적으로 잘가져온다.
		List<Employee> employees = departmentGet.getEmployees();
		assertEquals(2, employees.size());
		
		// 영속상태인 Department를 통해 가져온 Employee는 영속상태인가? 
		// 영속상태라면 변경추적으로 Update가 이루어질 것이다.
		Employee employeeInDepartment = employees.get(0);
		employeeInDepartment.setName("gunna");
		
		// Update구문이 잘 날라간다.
		entityManager.flush();
		
		// contains 메소드로 엔티티가 현재 영속상태인지 확인할 수 있다.
		boolean isManagedEntity = entityManager.contains(employeeInDepartment);
		
		// 영속상태인게 확인된다.
		assertEquals(true, isManagedEntity);
	}
}
