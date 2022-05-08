package com.example.insurance.data.entities;

import com.example.insurance.data.enumerations.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
@Accessors(chain = true)
@NoArgsConstructor
public class Order extends BaseEntity {

	private String subject;

	private String description;

	@Enumerated(EnumType.STRING)
	private OrderType type;

	private LocalDateTime deadline;

	private Double price;

	@ManyToOne
	@JoinColumn(name = "AUTHOR_USER_ID")
	private UserEntity authorUser;

	@ManyToOne
	@JoinColumn(name = "EXECUTOR_USER_ID")
	private UserEntity executorUser;

}
