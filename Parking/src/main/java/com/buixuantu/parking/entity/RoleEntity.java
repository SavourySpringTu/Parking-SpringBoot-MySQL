package com.buixuantu.parking.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Role")
public class RoleEntity {
	@Id
	private String id;
	private String name;
	@OneToMany(mappedBy="role",fetch = FetchType.EAGER)
	private List<EmployeeEntity> employeeList;
		
	
	public RoleEntity() {
		super();
	}
	
	public RoleEntity(String id, String name, List<EmployeeEntity> employeeList) {
		super();
		this.id = id;
		this.name = name;
		this.employeeList = employeeList;
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
	public List<EmployeeEntity> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<EmployeeEntity> employeeList) {
		this.employeeList = employeeList;
	}
}
