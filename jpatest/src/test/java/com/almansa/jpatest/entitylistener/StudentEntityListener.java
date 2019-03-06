package com.almansa.jpatest.entitylistener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class StudentEntityListener {
	@PrePersist
	public void prePersist(StudentWithEntityLister entity) {
		System.out.println("prePersist");
	}

	@PreRemove
	public void preRemove(StudentWithEntityLister entity) {
		System.out.println("preRemove");
	}

	@PreUpdate
	public void preUpdate(StudentWithEntityLister entity) {
		System.out.println("preUpdate");
	}

	@PostPersist
	public void postPersist(StudentWithEntityLister entity) {
		System.out.println("postPersist");
	}

	@PostRemove
	public void postRemove(StudentWithEntityLister entity) {
		System.out.println("postRemove");
	}

	@PostUpdate
	public void postUpdate(StudentWithEntityLister entity) {
		System.out.println("postUpdate");
	}

	@PostLoad
	public void postLoad(StudentWithEntityLister entity) {
		System.out.println("postLoad");
	}
}
