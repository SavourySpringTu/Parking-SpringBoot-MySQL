package com.buixuantu.parking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.buixuantu.parking.Service.PositionsService;
import com.buixuantu.parking.entity.PositionsEntity;

@Controller
@RequestMapping("/parking")
public class PositionController {
	@Autowired
	private PositionsService positionService;

	@GetMapping("/position")
	public String position(ModelMap model,@RequestParam("p") Optional<Integer> p) {
		page(model,p);
		return "position";
	}

	@PostMapping(value = "position", params = "btn_add_position")
	public String addPosition(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p) {
		if (positionService.findPositionById(id) != null) {
			page(model,p);
			model.addAttribute("mes", "Invalid Id");
			return "position";
		}
		positionService.addPosition(id, false);
		page(model,p);
		return "position";
	}

	@PostMapping(value = "position", params = "btn_delete_position")
	public String deletePosition(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p) {
		if (positionService.findPositionById(id) == null) {
			page(model,p);
			model.addAttribute("mes", "Id Not Found");
			return "position";
		}
		else if(positionService.findPositionById(id).isStatus()==true){
			page(model,p);
			model.addAttribute("mes", "Position has Car");
			return "position";
		}
		positionService.deletePositionById(id);
		page(model,p);
		return "position";
	}

	@PostMapping(value = "position", params = "btn_search_position")
	public String btnSearch(ModelMap model, @RequestParam("search_position") String id,@RequestParam("p") Optional<Integer> p) {
		if (id.equals("") == true) {
			page(model,p);
			return "position";
		} else if (positionService.findPositionById(id) == null) {
			List<TicketEntity> list = new ArrayList<>();
			model.addAttribute("positions", list);
			model.addAttribute("p",p.orElse(0));
			return "position";
		} else {
			List<PositionsEntity> list = new ArrayList<>();
			list.add(positionService.findPositionById(id));
			model.addAttribute("positions", list);
			model.addAttribute("p",p.orElse(0));
			return "position";
		}
	}

	public void page(ModelMap model,Optional<Integer> p) {
		Sort sort = Sort.by("id");
		Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
		Page<PositionsEntity> page = positionService.findAllPage(pageable);
		model.addAttribute("positions",page);
		model.addAttribute("p",p.orElse(0));
	}
}
