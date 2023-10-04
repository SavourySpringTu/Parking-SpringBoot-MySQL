package com.buixuantu.parking.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Employee")
public class EmployeeEntity {
	@Id
	private String id;
	private String name;
	private String password;
	
	@OneToMany(mappedBy="employee",fetch = FetchType.EAGER)
	private List<TicketEntity> ticketList;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_role")
	private RoleEntity role;
	
	public EmployeeEntity() {
		super();
	}
	
	public EmployeeEntity(String id,RoleEntity role,String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public List<TicketEntity> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<TicketEntity> ticketList) {
		this.ticketList = ticketList;
	}
	
}
