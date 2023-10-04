package com.buixuantu.parking.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Revenue")
public class RevenueEntity {
	@Id
	private LocalDate id;
	private int total;
	
	@OneToMany(mappedBy="revenue",fetch = FetchType.EAGER)
	private List<TicketEntity> ticketList = new ArrayList<>() ;
	
	public RevenueEntity() {
		super();
	}
	
	public RevenueEntity(LocalDate id) {
		super();
		this.id=id;
	}

	public LocalDate getId() {
		return id;
	}

	public void setId(LocalDate id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<TicketEntity> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<TicketEntity> ticketList) {
		this.ticketList = ticketList;
	}
}
