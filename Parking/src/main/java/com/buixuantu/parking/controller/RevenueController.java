package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/parking")
public class RevenueController {
	@Autowired
	private RevenueService revenueService;
	
	@GetMapping("/revenue")
	public String revenue(ModelMap model) {
		model.addAttribute("count",0);
		model.addAttribute("revenue",0);
		return "revenue";
	}

	@PostMapping("/revenue")
	public String statistical(ModelMap model, @RequestParam("ip_date")LocalDate date){
		if(revenueService.findRevenueById(date)==null){
			model.addAttribute("mes","Date Not Found");
			model.addAttribute("count",0);
			model.addAttribute("revenue",0);
			return "revenue";
		}
		model.addAttribute("count",revenueService.countCar(date));
		model.addAttribute("revenue",revenueService.findRevenueById(date).getTotal());
		return "revenue";
	}
}
