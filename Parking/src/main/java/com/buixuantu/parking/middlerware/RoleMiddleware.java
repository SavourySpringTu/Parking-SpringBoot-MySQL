package com.buixuantu.parking.middlerware;

import org.springframework.beans.factory.annotation.Autowired;

import com.buixuantu.parking.Service.EmployeeService;
import com.buixuantu.parking.entity.EmployeeEntity;

public class RoleMiddleware extends MiddlewareLogin{
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private EmployeeService employeeService;

	@Override
	public EmployeeEntity check(String username, String password) {
		System.out.println("zo role ne");
		if(employeeService.findEmployeeById(username).getRole().equals("Admin")==true || employeeService.findEmployeeById(username).getRole().equals("Cashier"))
		{
			return employeeService.findEmployeeById(username);
		}
		return checkNext(username,password);
	}

}
