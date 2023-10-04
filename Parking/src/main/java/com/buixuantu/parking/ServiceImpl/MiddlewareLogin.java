package com.buixuantu.parking.ServiceImpl;

import com.buixuantu.parking.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MiddlewareLogin {
	@Autowired
	private EmployeeService employeeService;

	private MiddlewareLogin next;
	
	public MiddlewareLogin setNextChain(MiddlewareLogin next) {
		this.next=next;
		return next;
	}
	public abstract String check(String username, String password,EmployeeService employeeService);
	
	protected String checkNext(String username, String password,EmployeeService em) {
		return next.check(username,password,em);
	}
}
