package com.buixuantu.parking.controller;

import com.buixuantu.parking.entity.EmployeeEntity;
import com.buixuantu.parking.entity.PositionsEntity;
import com.buixuantu.parking.entity.TicketEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.buixuantu.parking.Service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parking")
public class AdminController {
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/admin")
	public String admin(ModelMap model,@RequestParam("p") Optional<Integer> p) {
		page(model,p);
		return "admin";
	}
	
	@PostMapping(value="admin",params="btn_add_employee")
	public String addEmployee(ModelMap model,@RequestParam("ip_id") String id,@RequestParam("ip_role") String role,@RequestParam("ip_name") String name,@RequestParam("ip_password") String password,@RequestParam("p") Optional<Integer> p){
		if(employeeService.findEmployeeById(id)!=null) {
			page(model,p);
			model.addAttribute("mes","Invalid Id");
			return "admin";
		}
		employeeService.addEmployee(id,role,name,password);
		page(model,p);
		return "admin";
	}
	
	@PostMapping(value="admin",params="btn_update_employee")
	public String updateEmployee(ModelMap model,@RequestParam("ip_id") String id,@RequestParam("ip_role") String role,@RequestParam("ip_name") String name,@RequestParam("ip_password") String password,@RequestParam("p") Optional<Integer> p){
		if(employeeService.findEmployeeById(id)==null) {
			page(model,p);
			model.addAttribute("mes","Id Not Found");
			return "admin";
		}
		employeeService.updateEmployeeById(id,role,name,password);
		page(model,p);
		return "admin";
	}
	
	@PostMapping(value="admin",params="btn_delete_employee")
	public String deleteEmployee(ModelMap model,@RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p) {
		if(employeeService.findEmployeeById(id)==null) {
			page(model,p);
			model.addAttribute("mes","Id Not Found");
			return "admin";
		}
		else if(employeeService.findEmployeeById(id).getTicketList().size()!=0) {
			page(model,p);
			model.addAttribute("mes","Cannot Delete");
			return "admin";
		}
		employeeService.deleteEmployeeById(id);
		page(model,p);
		return "admin";
	}

	@PostMapping(value = "admin", params = "btn_search_employee")
	public String btnSearch(HttpServletRequest request, ModelMap model, @RequestParam("search_employee") String id,@RequestParam("p") Optional<Integer> p) {
		HttpSession session = request.getSession();
		if (id.equals("") == true) {
			page(model,p);
			return "admin";
		} else if (employeeService.findEmployeeById(id) == null) {
			List<TicketEntity> list = new ArrayList<>();
			model.addAttribute("employees", list);
			model.addAttribute("p",p.orElse(0));
			return "admin";
		} else {
			List<EmployeeEntity> list = new ArrayList<>();
			list.add(employeeService.findEmployeeById(id));
			model.addAttribute("employees", list);
			model.addAttribute("p",p.orElse(0));
			return "admin";
		}
	}
	public void page(ModelMap model, Optional<Integer> p) {
		Sort sort = Sort.by("id");
		Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
		System.out.println(employeeService.findAllPage(pageable));
		Page<EmployeeEntity> page = employeeService.findAllPage(pageable);
		model.addAttribute("employees",page);
		model.addAttribute("p",p.orElse(0));
	}
}
