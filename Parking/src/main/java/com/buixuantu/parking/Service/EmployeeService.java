package com.buixuantu.parking.Service;

import java.util.List;

import com.buixuantu.parking.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
	List<EmployeeEntity> getAllEmployee(); 
	EmployeeEntity findEmployeeById(String id);
	EmployeeEntity login(String u, String p);
	EmployeeEntity addEmployee(String id,String role,String name,String password);
	EmployeeEntity updateEmployeeById(String id,String role,String name,String password);
	void deleteEmployeeById(String id);
	Page<EmployeeEntity> findAllPage(Pageable pageable);
}
