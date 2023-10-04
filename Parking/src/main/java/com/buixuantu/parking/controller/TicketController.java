package com.buixuantu.parking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.buixuantu.parking.entity.EmployeeEntity;
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
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PositionsService positionService;
    @Autowired
    private RevenueService revenueService;

    @GetMapping("/ticket")
    public String home(HttpServletRequest request, ModelMap model,@RequestParam("p") Optional<Integer> p){
        HttpSession session = request.getSession();
        if(session.getAttribute("id_employee")==null)
        {
            page(model,p);
            return "login";
        }
        page(model,p);
        return "ticket";
    }

    @PostMapping(value="ticket",params="btn_confirm_ticket")
    public String addTicket(HttpServletRequest request,ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_price") int price,@RequestParam("ip_type") boolean type,@RequestParam("ip_number") int number,@RequestParam("ip_position") String position,@RequestParam("p") Optional<Integer> p) {
        HttpSession session = request.getSession();
        if(ticketService.findTicketById(id) != null || positionService.findPositionById(position)==null || ticketService.checkTicket(number)==false || positionService.checkPosition(position)==false) {
            model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
            page(model,p);
            model.addAttribute("mes","Eror");
            return "ticket";
        }

        ticketService.addTicket(id,(String )session.getAttribute("id_employee"),price,type,position,number);
        revenueService.updateRevenueDay();
        page(model,p);
        model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
        return "ticket";
    }

    @PostMapping(value="ticket",params="btn_update_ticket")
    public String updateTicket(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_price") int price,@RequestParam("ip_type") boolean type,@RequestParam("ip_number") int number,@RequestParam("ip_position") String position,@RequestParam("p") Optional<Integer> p) {
        LocalDate d =LocalDate.now();
        if(ticketService.findTicketById(id).isStatus()==true) {
            page(model,p);
            model.addAttribute("mes","Ticket has been Export");
            return "ticket";
        }
        else if(ticketService.findTicketById(id).getPrice()!=price && ticketService.findTicketById(id).getRevenue().getId()!=d){
            page(model,p);
            model.addAttribute("mes","Cannot Update Price");
            return "ticket";
        }
        ticketService.updateTicket(id, number,price, type,position);
        revenueService.updateRevenueDay();
        page(model,p);
        return "ticket";
    }

    @PostMapping(value="ticket",params="btn_export_ticket")
    public String exportTicket(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p) {
        if(ticketService.findTicketById(id) == null) {
            page(model,p);
            model.addAttribute("mes","Id Not Found");
            return "ticket";
        }
        else if(ticketService.findTicketById(id).isStatus()==true) {
            page(model,p);
            model.addAttribute("mes","Ticket has been Export");
            return "ticket";
        }
        ticketService.exportTicket(id);
        page(model,p);
        return "ticket";
    }

    // ======================== SEARCH ===========================

    @PostMapping(value = "ticket", params = "btn_search_ticket")
    public String btnSearch(HttpServletRequest request, ModelMap model,@RequestParam("search_ticket") String id,@RequestParam("p") Optional<Integer> p) {
        HttpSession session = request.getSession();
        if(id.equals("")==true){
            page(model,p);
            model.addAttribute("id_employee",(String )session.getAttribute("id_employee"));
            return "ticket";
        }
        else if(ticketService.findTicketById(id)==null){
            List<TicketEntity> list = new ArrayList<>();
            model.addAttribute("tickets", list);
            model.addAttribute("id_employee", (String) session.getAttribute("id_employee"));
            model.addAttribute("p",p.orElse(0));
            return "ticket";
        }
        else {
            List<TicketEntity> list = new ArrayList<>();
            list.add(ticketService.findTicketById(id));
            model.addAttribute("tickets", list);
            model.addAttribute("id_employee", (String) session.getAttribute("id_employee"));
            model.addAttribute("p",p.orElse(0));
            return "ticket";
        }
    }
    public void page(ModelMap model, Optional<Integer> p) {
        Sort sort = Sort.by("id");
        Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
        System.out.println(ticketService.findAllPage(pageable));
        Page<TicketEntity> page = ticketService.findAllPage(pageable);
        model.addAttribute("tickets",page);
        model.addAttribute("p",p.orElse(0));
    }
}
