package com.buixuantu.parking.controller;

import com.buixuantu.parking.Repository.WarehouseRepository;
import com.buixuantu.parking.Service.WarehouseService;
import com.buixuantu.parking.entity.TicketEntity;
import com.buixuantu.parking.entity.WarehouseEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parking")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouse")
    public String warehouse(ModelMap model,@RequestParam("p") Optional<Integer> p){
        page(model,p);
        return "warehouse";
    }

    @PostMapping(value="warehouse",params = "btn_export_warehouse")
    public String btnExport(ModelMap model, @RequestParam("ip_ticket") String ticket,@RequestParam("p") Optional<Integer> p){
        if(warehouseService.findWarehouseByTicket(ticket)==null){
            page(model,p);
            model.addAttribute("mes","Ticket Not Found");
            return "warehouse";
        }
        warehouseService.exportWarehouseTicket(ticket);
        page(model,p);
        return "warehouse";
    }

    @PostMapping(value = "warehouse", params = "btn_search_warehouse")
    public String btnSearch(ModelMap model, @RequestParam("search_warehouse") String id1, @RequestParam("p") Optional<Integer> p) {
        if(id1.equals("")==true){
            page(model,p);
            return "warehouse";
        }
        long id = Integer.parseInt(id1);
        if(warehouseService.findWarehouseById(id)==null){
            List<TicketEntity> list = new ArrayList<>();
            model.addAttribute("warehouses",list);
            model.addAttribute("p",p.orElse(0));
            return "warehouse";
        }
        else if(warehouseService.findWarehouseById(id)!=null) {
            List<WarehouseEntity> list = new ArrayList<>();
            list.add(warehouseService.findWarehouseById(id));
            model.addAttribute("warehouses",list);
            model.addAttribute("p",p.orElse(0));
            return "warehouse";
        }
        return "warehouse";
    }
    public void page(ModelMap model,@RequestParam("p") Optional<Integer> p){
        Sort sort = Sort.by("id");
        Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
        System.out.println(warehouseService.findAllPage(pageable));
        Page<WarehouseEntity> page = warehouseService.findAllPage(pageable);
        model.addAttribute("warehouses",page);
        model.addAttribute("p",p.orElse(0));
    }
}
