package com.almansa.jpatest.mapping.immutable;

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
public class ImmutableFieldTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void Immutable_어노테이션을_붙인_엔티티() {
		Employee employee = new Employee();
		employee.setName("NaYunsu");

		entityManager.persist(employee);

		entityManager.flush();
		entityManager.clear();

		// 업데이트 작업검
		Employee employeeForUpdate = entityManager.find(Employee.class, employee.getId());
		employeeForUpdate.setName("NaJinsu");

		// 오브젝트 수준에서는 필드값이 변경되었다.
		assertEquals("NaJinsu", employeeForUpdate.getName());

		// 캐시에서 오브젝트를 가져와서 검증한다.
		// 캐시에도 변한 값이 들어가 있다.
		Employee employeeInCache = entityManager.find(Employee.class, employee.getId());
		assertEquals("NaJinsu", employeeInCache.getName());

		// 캐시클리어
		entityManager.flush();
		entityManager.clear();

		// 업데이트 추적이 이루어지지 않았다.
		// 별다른 예외없이 업데이트 작업이 무시되었다.
		Employee employeeForCheckUpdate = entityManager.find(Employee.class, employee.getId());
		assertEquals("NaYunsu", employeeForCheckUpdate.getName());

		// 삭제작업 검증
		entityManager.remove(employeeForCheckUpdate);

		entityManager.flush();
		entityManager.clear();
		
		// 삭제작업은 Immutable 어노테이션과 관계없이 잘 동작한다.
		Employee employeeForCheckDelete = entityManager.find(Employee.class, employee.getId());
		assertEquals(true, employeeForCheckDelete == null);
	}
}
