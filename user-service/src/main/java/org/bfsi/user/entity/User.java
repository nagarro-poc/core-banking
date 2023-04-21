package org.bfsi.user.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private String contactNumber;
	
	@Column
	private String emailId;
	
	@Column
	private String address;
	
	@Column(length =10,unique = true)
	private String userName;
	
	/*
	 * @Column
	 * 
	 * @DefaultValue(value=CurrentTimestamp) private Date createdAt;
	 */
}
