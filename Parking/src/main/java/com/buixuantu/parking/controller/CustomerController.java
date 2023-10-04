package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.CustomerService;
import com.buixuantu.parking.Service.TicketService;
import com.buixuantu.parking.entity.CustomerEntity;
import com.buixuantu.parking.entity.TicketEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parking")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/customer")
    public String customer(ModelMap model,@RequestParam("p") Optional<Integer> p){
        page(model,p);
        return "customer";
    }
    @PostMapping(value = "customer",params = "btn_add_customer")
    public String addCustomer(ModelMap model,@RequestParam("ip_name")String name,@RequestParam("ip_email")String email,@RequestParam("ip_password")String password,@RequestParam("p") Optional<Integer> p) {
        if(customerService.findCustomerByEmail(email)!=null){
            page(model,p);
            model.addAttribute("mess","EROR");
            return "customer";
        }
        else{
            customerService.addCustomer(email,name,password);
            page(model,p);
            return "customer";
        }
    }
    @PostMapping(value = "customer",params = "btn_update_customer")
    public String updateCustomer(ModelMap model,@RequestParam("ip_name")String name,@RequestParam("ip_email")String email,@RequestParam("ip_password")String password,@RequestParam("p") Optional<Integer> p) {
        if(customerService.findCustomerByEmail(email)==null){
            page(model,p);
            model.addAttribute("mess","EROR");
            return "customer";
        }
        else{
            customerService.updateCustomer(email,name,password);
            page(model,p);
            return "customer";
        }
    }
    @PostMapping(value = "customer",params = "btn_delete_customer")
    public String deleteCustomer(ModelMap model,@RequestParam("ip_email")String email,@RequestParam("p") Optional<Integer> p) {
        if(customerService.findCustomerByEmail(email)==null){
            page(model,p);
            model.addAttribute("mess","EROR");
            return "customer";
        }
        else if(ticketService.checkDeleteCustomer(email)!=null){
            page(model,p);
            model.addAttribute("mess","Can't Delete");
            return "customer";
        }
        else{
            customerService.deleteCustomerByEmail(email);
            page(model,p);
            return "customer";
        }
    }
    @PostMapping(value = "customer", params = "btn_search_customer")
    public String btnSearch(HttpServletRequest request, ModelMap model, @RequestParam("search_customer") String email,@RequestParam("p") Optional<Integer> p) {
        if(customerService.findCustomerByEmail(email)==null){
            List<TicketEntity> list = new ArrayList<>();
            model.addAttribute("customers", list);
            model.addAttribute("p",p.orElse(0));
            return "customer";
        }
        else {
            List<CustomerEntity> list = new ArrayList<>();
            list.add(customerService.findCustomerByEmail(email));
            model.addAttribute("customers", list);
            model.addAttribute("p",p.orElse(0));
            return "customer";
        }
    }
    public void page(ModelMap model,@RequestParam("p") Optional<Integer> p){
        Sort sort = Sort.by("email");
        Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
        model.addAttribute("customers",customerService.findAllPage(pageable));
        model.addAttribute("p",p.orElse(0));
    }
}
