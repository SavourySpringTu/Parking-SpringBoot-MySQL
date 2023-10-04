package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.CustomerService;
import com.buixuantu.parking.Service.TicketService;
import com.buixuantu.parking.entity.CustomerEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class LoginUserController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/login_user")
    public String login(){
        return "login_user";
    }

    @RequestMapping (value = "login_user",params = "btn_login_user",method= RequestMethod.POST)
    public String btnLogin(ModelMap model, HttpSession session ,@RequestParam("ip_user") String user, @RequestParam("ip_password") String password){
        if(customerService.login(user,password)==null){
            return "login_user";
        }
        else{
            session.setAttribute("customer", user);
            model.addAttribute("email",user);
            model.addAttribute("tickets",ticketService.listTicketofCustomer(user));
            return "user";
        }
    }
}
