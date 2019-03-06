package com.almansa.jpatest.entitymanager;

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

import com.almansa.jpatest.config.AppConfig;

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
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");

		assertEquals(true, Objects.isNull(student.getId()));

		// persist로 객체를 영속상태로 만든다.해당 객체는 영속성 컨테스트에서 관리될 것이다.
		// 아래 시점에 시퀀스테이블에서 새로은 PK값을 조회해 엔티티에 채워넣는다.
		entityManager.persist(student);
		assertEquals(true, Objects.nonNull(student.getId()));

		// 실제 Insert구문이 날라간다.
		entityManager.flush();
	}

	@Test(expected = PersistenceException.class)
	public void 데이터베이스_컬럼길이_초과_엔티티저장() {
		String overSizeFirstName = "over size first name";

		Student student = new Student();
		student.setFirstName(overSizeFirstName);
		student.setLastName("Yunsu");

		entityManager.persist(student);

		// 아래구문을 제외하니까 예외가 발생하지 않는다.
		// persist()까지는 1차캐시에서 관리되고 실제 DB와 동기화되지 않는다.
		entityManager.flush();
	}

	@Test
	public void JPQL로_하나의_엔티티_가져오기() {
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");
		entityManager.persist(student);
		
		entityManager.flush();
		entityManager.clear();
		
		TypedQuery<Student> query = entityManager.createQuery("SELECT a FROM Student a Where a.lastName = :lastName",
				Student.class);

		query.setParameter("lastName", "Yunsu");
		Student studentGet = query.getSingleResult(); // 만약 데이터가 여러건이 조회되었다면?

		assertEquals("Na", studentGet.getFirstName());
	}

	@Test(expected = NonUniqueResultException.class)
	public void getSingleResult의_결과가_여러건이라면() {
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");
		entityManager.persist(student);

		Student student2 = new Student();
		student2.setFirstName("Na");
		student2.setLastName("Jinsu");
		entityManager.persist(student2);

		entityManager.flush();
		entityManager.clear();
		
		TypedQuery<Student> query = entityManager
				.createQuery("SELECT a FROM Student a Where a.firstName = :firstName", Student.class);
		query.setParameter("firstName", "Na");

		// 실제 데이터베이스에서 가져온 것은 2개지만 getSingleResult 호출하니 NonUniqueResultException을 던진다.
		query.getSingleResult();
	}

	@Test
	public void 하나의_TypedQuery인스턴스로_여러번의_결과리턴_메소드호출() {
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");
		entityManager.persist(student);

		Student student2 = new Student();
		student2.setFirstName("Na");
		student2.setLastName("Jinsu");
		entityManager.persist(student2);

		entityManager.flush();
		entityManager.clear();
		
		TypedQuery<Student> query = entityManager
				.createQuery("SELECT a FROM Student a Where a.firstName = :firstName", Student.class)
				.setParameter("firstName", "Na");

		List<Student> resultList1 = query.getResultList();
		assertEquals(2, resultList1.size());

		// 하나의 Query 인스턴스로 결과리턴 메소드를 여러번 호출하여도 문제없으며 결과도 동일하다
		List<Student> resultList2 = query.getResultList();
		assertEquals(2, resultList2.size());
	}

	@Test
	public void paging기법으로_데이터가져오기() {
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");
		entityManager.persist(student);

		Student student2 = new Student();
		student2.setFirstName("Na");
		student2.setLastName("Jinsu");
		entityManager.persist(student2);

		Student student3 = new Student();
		student3.setFirstName("Kim");
		student3.setLastName("Yunmi");
		entityManager.persist(student3);

		Student student4 = new Student();
		student4.setFirstName("Na");
		student4.setLastName("Jangsu");
		entityManager.persist(student4);

		entityManager.flush();
		entityManager.clear();
		
		// fluent스타일의 API를 제공한다.
		TypedQuery<Student> query = entityManager
				.createQuery("SELECT a FROM Student a Where a.firstName = :firstName", Student.class)
				.setParameter("firstName", "Na")
				.setFirstResult(1)
				.setMaxResults(2);
		List<Student> students = query.getResultList();

		assertEquals("Jinsu", students.get(0).getLastName());
		assertEquals("Jangsu", students.get(1).getLastName());
	}

	@Test
	public void 엔티티의_영속상태_변경시키기() {
		// 비영속. 순수 자바객체
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");

		// 영속상태. Id가 채워졌다.
		entityManager.persist(student);
		assertEquals(true, Objects.nonNull(student.getId()));

		// 준영속상태. Id는 계속 남아있다. 
		entityManager.detach(student);
		assertEquals(true, Objects.nonNull(student.getId()));
		student.setLastName("Jinsu");
		
		// 영속상태. 준영속상태의 엔티티를 다시 영속상태로 만든다.
		entityManager.merge(student);		
		entityManager.flush();
	}
	
	@Test
	public void 비영속객체를_merge시킨다면() {
		// 비영속. 순수 자바객체
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");

		entityManager.merge(student);			
		
		// insert구문이 정상적으로 날아간다.
		// 병합은 준영속, 비영속을 신경쓰지 않는다. 
		entityManager.flush();
	}
	
	@Test
	public void detach후_update감지_실험() {
		Student student = new Student();
		student.setFirstName("Na");
		student.setLastName("Yunsu");
		
		entityManager.persist(student);
		entityManager.flush();
		
		entityManager.detach(student);		
		student.setLastName("Jinsu");
		
		Student studentGet = entityManager.find(Student.class, student.getId());		
		assertEquals("Yunsu", studentGet.getLastName());
	}
}
