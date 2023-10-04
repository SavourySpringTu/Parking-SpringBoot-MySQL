package com.buixuantu.parking.middlerware;

import org.springframework.beans.factory.annotation.Autowired;

import com.buixuantu.parking.Service.EmployeeService;
import com.buixuantu.parking.entity.EmployeeEntity;

public class UserMiddleware extends MiddlewareLogin{
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private EmployeeService employeeService;
	
	private String name;
	private String password;
	

	public UserMiddleware(String name, String password) {
		this.name=name;
		this.password=password;
	}

	@Override
	public EmployeeEntity check(String username, String password) {
		System.out.println(employeeService.login(username, password));
		if(employeeService.login(username, password)!=null) {
			System.out.println("zo role");
			return checkNext(username,password);
		}
		return null;
	}

}
