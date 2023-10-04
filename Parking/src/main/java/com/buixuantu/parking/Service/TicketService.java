package com.buixuantu.parking.Service;

import java.time.LocalDate;
import java.util.List;

import com.buixuantu.parking.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
	TicketEntity findTicketById(String id);
	List<TicketEntity> getAllTicket();
	TicketEntity addTicket(String id,String employee,int price,boolean type,String position,int number);
	void updateTicket(String id,int number,int price,boolean type,String position);
	void exportTicket(String id);
	boolean checkTicket(int number);
	List<TicketEntity> getTicket();
	Page<TicketEntity> findAllPage(Pageable pageable);
	List<TicketEntity> listTicketofCustomer(String id);
	void customerConectTicket(String id,String email);
	TicketEntity checkDeleteCustomer(String email);
}
