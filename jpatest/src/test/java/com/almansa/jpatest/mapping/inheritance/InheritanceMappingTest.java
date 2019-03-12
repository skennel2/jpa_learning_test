package com.almansa.jpatest.mapping.inheritance;

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
public class InheritanceMappingTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void 단테이블_상속매핑() {
		Pen pen = new Pen();
		pen.setName("red pen");
		pen.setPrice(3000L);
		pen.setColor("Red");
				
		Book book = new Book();
		book.setName("Head First C#");
		book.setPrice(25000L);
		book.setAuthor("앤드류 스텔만"); 
		
		// 만약 Pen의 color필드 속성이 nullable = false 라면
		// book을 영속화할때 DB제약조건 예외가 터질것이다. 
		// book에서 color를 null이 아닌 값으로 초기화할 방법이 없기때문이다.
		entityManager.persist(pen);
		entityManager.persist(book);
		
		// 테이블 생성 쿼리를 살펴보면 Product라는 테이블이 생기고 
		// 거기에 모든 자식 엔티티의 필드값들이 컬럼으로 생성되고
		// 자식 엔티티는 D_TYPE 이라는 필드에 값으로 구분된다.
		entityManager.flush();
		entityManager.clear();				
	}
}
