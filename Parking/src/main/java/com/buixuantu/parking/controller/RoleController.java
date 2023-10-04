package com.buixuantu.parking.controller;

import com.buixuantu.parking.Service.RoleService;
import com.buixuantu.parking.entity.PositionsEntity;
import com.buixuantu.parking.entity.RoleEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parking")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("role")
    public String role(ModelMap model,@RequestParam("p") Optional<Integer> p){
        page(model,p);
        return "role";
    }
    @PostMapping(value = "role",params = "btn_add_role")
    public String addRole(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_name") String name,@RequestParam("p") Optional<Integer> p){

        if (roleService.findRoleById(id)!=null){
            model.addAttribute("mes","Id Invalid");
            page(model,p);
            return "role";
        }
        else{
            roleService.addRole(id,name);
            page(model,p);
            return "role";
        }
    }

    @PostMapping(value = "role",params = "btn_update_role")
    public String updateRole(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("ip_name") String name,@RequestParam("p") Optional<Integer> p){
        if (roleService.findRoleById(id)==null){
            model.addAttribute("mes","Id Not Found");
            page(model,p);
            return "role";
        }
        else{
            roleService.updateRole(id,name);
            page(model,p);
            return "role";
        }
    }

    @PostMapping(value = "role",params = "btn_delete_role")
    public String deleteRole(ModelMap model, @RequestParam("ip_id") String id,@RequestParam("p") Optional<Integer> p){
        if (roleService.findRoleById(id)==null){
            model.addAttribute("mes","Id Not Found");
            page(model,p);
            return "role";
        }
        else if(roleService.findRoleById(id).getEmployeeList().size()!=0){
            model.addAttribute("mes","Id Can't Delete");
            page(model,p);
            return "role";
        }
        else{
            roleService.deleteRoleById(id);
            page(model,p);
            return "role";
        }
    }
    @PostMapping(value = "role", params = "btn_search_role")
    public String btnSearch(HttpServletRequest request, ModelMap model, @RequestParam("search_role") String id,@RequestParam("p") Optional<Integer> p) {
        HttpSession session = request.getSession();
        if(id.equals("")==true){
            page(model,p);
            return "role";
        }
        else if(roleService.findRoleById(id)==null){
            List<RoleEntity> list = new ArrayList<>();
            model.addAttribute("roles", list);
            model.addAttribute("p",p.orElse(0));
            return "role";
        }
        else {
            System.out.println(id);
            List<RoleEntity> list = new ArrayList<>();
            list.add(roleService.findRoleById(id));
            model.addAttribute("roles", list);
            model.addAttribute("p",p.orElse(0));
            return "role";
        }
    }
    public void page(ModelMap model, Optional<Integer> p) {
        Sort sort = Sort.by("id");
        Pageable pageable = PageRequest.of(p.orElse(0), 5,sort);
        Page<RoleEntity> page = roleService.findAllPage(pageable);
        model.addAttribute("roles",page);
        model.addAttribute("p",p.orElse(0));
    }
}
