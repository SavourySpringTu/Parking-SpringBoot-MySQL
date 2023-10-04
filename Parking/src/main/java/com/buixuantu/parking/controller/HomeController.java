package com.buixuantu.parking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.buixuantu.parking.Service.PositionsService;
import com.buixuantu.parking.Service.RevenueService;
import com.buixuantu.parking.Service.TicketService;
import com.buixuantu.parking.entity.TicketEntity;

@Controller
@RequestMapping("/parking")
public class HomeController {
	@Autowired
	private TicketService ticketService;
	@Autowired
	private PositionsService positionService;
	@Autowired
	private RevenueService revenueService;
	
	@GetMapping("/home")
	public String home(HttpServletRequest request, ModelMap model,@RequestParam("p") Optional<Integer> p){
		HttpSession session = request.getSession();
		if(session.getAttribute("id_employee")==null)
		{
			return "login";
		}
		page(model,p);
		return "home";
	}
	
	@PostMapping(value="home",params="btn_confirm_ticket")
	public String addTicket(HttpServletRequest request,ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_price") int price,@RequestParam("ip_type") boolean type,@RequestParam("ip_number") int number,@RequestParam("ip_position") String position,@RequestParam("p") Optional<Integer> p) {
		HttpSession session = request.getSession();
		System.out.println("zo day");
		if(ticketService.findTicketById(id) != null || positionService.findPositionById(position)==null || ticketService.checkTicket(number)==false || positionService.checkPosition(position)==false) {
			page(model,p);
			model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
			model.addAttribute("mes","Eror");
			return "home";
		}
		else{
			ticketService.addTicket(id,(String )session.getAttribute("id_employee"),price,type,position,number);
			revenueService.updateRevenueDay();
			page(model,p);
			model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
			return "home";
		}
	}
	
	@PostMapping(value="home",params="btn_update_ticket")
	public String updateTicket(HttpServletRequest request,ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_price") int price,@RequestParam("ip_type") boolean type,@RequestParam("ip_number") int number,@RequestParam("ip_position") String position,@RequestParam("p") Optional<Integer> p) {
		LocalDate d =LocalDate.now();
		if(ticketService.findTicketById(id)==null){
			page(model,p);
			model.addAttribute("mes","Ticket Not Found");
			return "home";
		}
		else if(ticketService.findTicketById(id).isStatus()==true) {
			page(model,p);
			model.addAttribute("mes","Ticket has been Export");
			return "home";
		}
		else if(ticketService.findTicketById(id).getPrice()!=price && ticketService.findTicketById(id).getRevenue().getId().equals(d)==false){
			page(model,p);
			model.addAttribute("mes","Cannot Update Price");
			return "home";
		}
		else{
			ticketService.updateTicket(id, number,price, type,position);
			revenueService.updateRevenueDay();
			page(model,p);
			return "home";
		}
	}
	
	@PostMapping(value="home",params="btn_export_ticket")
	public String exportTicket(HttpServletRequest request,ModelMap model, @RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p) {
		if(ticketService.findTicketById(id) == null) {
			page(model,p);
			model.addAttribute("mes","Id Not Found");
			return "home";
		}
		else if(ticketService.findTicketById(id).isStatus()==true) {
			page(model,p);
			model.addAttribute("mes","Ticket has been Export");
			return "home";
		}
		ticketService.exportTicket(id);
		page(model,p);
		return "home";
	}

	// ======================== SEARCH ===========================

	@PostMapping(value = "home", params = "btn_search_ticket")
	public String btnSearch(HttpServletRequest request, ModelMap model,@RequestParam("search_ticket") String id,@RequestParam("p") Optional<Integer> p) {
		HttpSession session = request.getSession();
		if(id.equals("")==true){
			page(model,p);
			model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
			return "home";
		}
		else if(ticketService.findTicketById(id)==null){
			List<TicketEntity> list = new ArrayList<>();
			model.addAttribute("tickets", list);
			model.addAttribute("id_employee", (String) session.getAttribute("id_employee"));
			model.addAttribute("p",p.orElse(0));
			return "home";
		}
		else {
			List<TicketEntity> list = new ArrayList<>();
			list.add(ticketService.findTicketById(id));
			model.addAttribute("tickets", list);
			model.addAttribute("id_employee", (String) session.getAttribute("id_employee"));
			model.addAttribute("p",p.orElse(0));
			return "home";
		}
	}
	public void page(ModelMap model, Optional<Integer> p) {
		Sort sort = Sort.by("id");
		Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
		model.addAttribute("tickets",ticketService.findAllPage(pageable));
		model.addAttribute("p",p.orElse(0));
	}
}
