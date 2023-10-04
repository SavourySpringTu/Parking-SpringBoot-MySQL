package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.CustomerService;
import com.buixuantu.parking.Service.TicketService;
import com.buixuantu.parking.entity.CustomerEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/client")
public class UserController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/user")
    public String client(HttpServletRequest request, ModelMap model){
        HttpSession session = request.getSession();
        if(session.getAttribute("customer").equals("")==true){
            return "login_user";
        }
        model.addAttribute("email",(String) session.getAttribute("customer"));
        model.addAttribute("tickets",ticketService.listTicketofCustomer((String) session.getAttribute("customer")));
        return "user";
    }
    @PostMapping(value = "user",params = "btn_confirm_warehouse")
    public String btnWarehouse(HttpServletRequest request, ModelMap model, @RequestParam("ip_ticket")String ticket,@RequestParam("ip_number") int number){
        HttpSession session = request.getSession();
        ticketService.customerConectTicket(ticket, (String) session.getAttribute("customer"));
        model.addAttribute("tickets",ticketService.listTicketofCustomer((String) session.getAttribute("customer")));
        return "user";
    }
}
