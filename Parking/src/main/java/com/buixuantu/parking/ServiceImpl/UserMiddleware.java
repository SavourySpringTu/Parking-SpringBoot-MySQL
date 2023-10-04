package com.buixuantu.parking.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.buixuantu.parking.Service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class UserMiddleware extends MiddlewareLogin {

	@Override
	public String check(String username, String password,EmployeeService employeeService) {
		System.out.println("abc: "+employeeService.findEmployeeById(username));
		if(employeeService.login(username,password)==null){
			System.out.println("null ne");
			return null;
		}else{
			System.out.println("tiep ne");
			return checkNext(username,password,employeeService);
		}
	}
}
