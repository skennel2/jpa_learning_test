package com.almansa.jpatest.mapping.auditing;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class EntityBase {

	@Id
	@GeneratedValue
	private Long id;

	/*
	 * @CreatedDate을 동작하게 하려면
	 * Configuration클래스에 @EnableJpaAuditing 어노테이션을 추가한다.
	 * @Temporal(TemporalType.TIMESTAMP) @Temporal은 Date클래스만 지원한다.
	 */
	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime lastModifiedDate;

	protected EntityBase() {
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
}
