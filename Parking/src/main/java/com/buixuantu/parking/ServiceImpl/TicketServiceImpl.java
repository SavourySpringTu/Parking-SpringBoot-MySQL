package com.buixuantu.parking.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import com.buixuantu.parking.Service.*;
import com.buixuantu.parking.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buixuantu.parking.Repository.RevenueRepository;
import com.buixuantu.parking.Repository.TicketRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

@Service
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private PositionsService positionService;
	@Autowired
	private RevenueService revenueService;
	@Autowired
	private EmployeeService employeeService; 
	@Autowired
	private RevenueRepository revenueRepository;
	@Autowired
	private CustomerService customerService;
	
	@Override
	public List<TicketEntity> getAllTicket() {
		return ticketRepository.findAll();
	}

	@Override
	public TicketEntity addTicket(String id,String employee,int price,boolean type,String position,int number) {
		// ============== time =================
		LocalDateTime t = LocalDateTime.now();
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String tu=t.format(formatter);
		LocalDateTime time = LocalDateTime.parse(tu, formatter);
		//====================================
		
		PositionsEntity ps = positionService.findPositionById(position);
		ps.setStatus(true);
		EmployeeEntity em = employeeService.findEmployeeById(employee);
		RevenueEntity rv = revenueService.findRevenueById(date);
		if(rv==null) {
			rv = new RevenueEntity(date);
		}
		TicketEntity tc = new TicketEntity(id,time,type,price,number,false,em,rv,ps);
		ticketRepository.save(tc);
		List<TicketEntity> list = rv.getTicketList();
		list.add(tc);
		rv.setTicketList(list);
		revenueRepository.save(rv);
		return ticketRepository.save(tc);
	}

	@Override
	public TicketEntity findTicketById(String id) {
		return ticketRepository.findById(id).orElse(null);
	}

	@Override
	public void updateTicket(String id, int number, int price, boolean type,String position) {
		TicketEntity tmp = ticketRepository.findById(id).orElse(null);
		tmp.getPosition().setStatus(false);
		PositionsEntity ps = positionService.findPositionById(position);
		ps.setStatus(true);
		tmp.setPosition(positionService.findPositionById(position));
		tmp.setNumber_car(number);
		tmp.setPrice(price);
		tmp.setTicket_type(type);
		ticketRepository.save(tmp);
	}

	@Override
	public void exportTicket(String id) {
		TicketEntity tmp = ticketRepository.findById(id).orElse(null);
		tmp.setStatus(true);
		tmp.getPosition().setStatus(false);
		ticketRepository.save(tmp);
	}

	@Override
	public boolean checkTicket(int number) {
		for(TicketEntity a : ticketRepository.findAll()) {
			if(a.getNumber_car() ==number && a.isStatus()==false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<TicketEntity> getTicket() {
		List<TicketEntity> list = new ArrayList<>();
		for(TicketEntity a : ticketRepository.findAll()){
			if(a.isStatus()==false){
				list.add(a);
			}
		}
		return list;
	}

	@Override
	public Page<TicketEntity> findAllPage(Pageable pageable) {
		return ticketRepository.findAll(pageable);
	}

	@Override
	public List<TicketEntity> listTicketofCustomer(String id) {
		List<TicketEntity> list = new ArrayList<>();
		for(TicketEntity tc:ticketRepository.findAll()){
			if(tc.getCustomer()!=null){
				if(tc.getCustomer().getEmail().equals(id)==true){
					list.add(tc);
				}
			}
		}
		System.out.println("xong ne");
		return list;
	}

	@Override
	public void customerConectTicket(String id,String email) {
		for(TicketEntity tc : ticketRepository.findAll()){
			if((tc.getId()).equals(id)==true){
				CustomerEntity cs = customerService.findCustomerByEmail(email);
				tc.setCustomer(cs);
				ticketRepository.save(tc);
				System.out.println(tc.getCustomer().getEmail());
				return;
			}
		}
	}

	@Override
	public TicketEntity checkDeleteCustomer(String email) {
		for(TicketEntity tc:ticketRepository.findAll()){
			if(tc.getCustomer()!=null){
				if(tc.getCustomer().getEmail().equals(email)==true){
					return tc;
				}
			}
		}
		return null;
	}

}
