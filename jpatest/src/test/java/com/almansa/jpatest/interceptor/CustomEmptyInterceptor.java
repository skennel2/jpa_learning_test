package com.almansa.jpatest.interceptor;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

/*
 * 필요한 인터셉터 메소드만 재정의해서 사용할 수 있게 EmptyInterceptor를 제공한다.
 */
@Component
public class CustomEmptyInterceptor extends EmptyInterceptor implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void postFlush(Iterator entities) {		
		System.out.println("------------------postFlush");
	}
}
