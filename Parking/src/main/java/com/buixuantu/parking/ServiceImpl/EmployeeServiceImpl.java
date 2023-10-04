package com.buixuantu.parking.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buixuantu.parking.Repository.EmployeeRepository;
import com.buixuantu.parking.Service.EmployeeService;
import com.buixuantu.parking.Service.RoleService;
import com.buixuantu.parking.entity.EmployeeEntity;
import com.buixuantu.parking.entity.RoleEntity;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private RoleService roleSerice;
	
	@Override
	public List<EmployeeEntity> getAllEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public EmployeeEntity login(String u, String p) {
		System.out.println(u);
		System.out.println(p);
		if(employeeRepository.findById(u).orElse(null)!=null && employeeRepository.findById(u).orElse(null).getPassword().equals(p)==true)
		{
			return employeeRepository.findById(u).orElse(null);
		}
		else{
			return null;
		}
	}
	
	@Override
	public EmployeeEntity addEmployee(String id,String role,String name, String password) {
		RoleEntity r = roleSerice.findRoleById(role);
		EmployeeEntity tmp = new EmployeeEntity(id,r,name,password);
		return employeeRepository.save(tmp);
	}
	
	@Override
	public EmployeeEntity updateEmployeeById(String id,String role,String name, String password) {
		EmployeeEntity tmp = employeeRepository.findById(id).orElse(null);
		tmp.setName(name);
		tmp.setPassword(password);
		tmp.setRole(roleSerice.findRoleById(role));
		return employeeRepository.save(tmp);
	}
	@Override
	public void deleteEmployeeById(String id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Page<EmployeeEntity> findAllPage(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	@Override
	public EmployeeEntity findEmployeeById(String id) {
		return employeeRepository.findById(id).orElse(null);
	}
}
