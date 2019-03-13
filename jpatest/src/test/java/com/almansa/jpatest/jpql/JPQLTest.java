package com.almansa.jpatest.jpql;

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
public class JPQLTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void find와_jpql의_차이() {
		Person person = new Person();
		person.setName("Nayunsu");

		entityManager.persist(person);
		entityManager.flush();

		// find를 통해서 조회하면 먼저 1차 캐시에서 엔티티를 찾고, 없을때 DB에서 조회해서 1차 캐시에 적재한다.
		Person personFind = entityManager.find(Person.class, person.getId());
		assertEquals(true, person == personFind);

		// JPQL로 조회하면 무조건 DB에서 조해해서 1차캐시에 적재한다. 
		// 적재시 같은 엔티티가 존재하면 조회한것으로 대체한다.
		Person personJPQL = entityManager.createQuery("SELECT a FROM Person a WHERE a.id = :id", Person.class)
				.setParameter("id", person.getId())
				.getSingleResult();
		assertEquals(true, person == personJPQL);
	}
}
