package com.buixuantu.parking.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.buixuantu.parking.Service.EmployeeService;

public class RoleMiddleware extends MiddlewareLogin {
	@Override
	public String check(String username, String password,EmployeeService employeeService) {
			return employeeService.findEmployeeById(username).getRole().getName();
	}

}
