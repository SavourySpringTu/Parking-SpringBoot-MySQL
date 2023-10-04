package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/client")
public class RegisterController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping(value = "register",params = "btn_register")
    public String btnRegister(ModelMap model, @RequestParam("ip_email") String email, @RequestParam("ip_name") String name, @RequestParam("ip_password") String password, @RequestParam("ip_password1") String password1){
        if(customerService.findCustomerByEmail(email)!=null){
            model.addAttribute("mes","Email Invalid");
            return "register";
        }
        else if(password1.equals(password)==false){
            model.addAttribute("mes","Password Eror");
            return "register";
        }
        else {
            customerService.addCustomer(email,name,password);
            return "register";
        }
    }

}
