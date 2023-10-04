package com.buixuantu.parking.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buixuantu.parking.Repository.RevenueRepository;
import com.buixuantu.parking.Service.RevenueService;
import com.buixuantu.parking.entity.RevenueEntity;
import com.buixuantu.parking.entity.TicketEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
public class RevenueServiceImpl implements RevenueService{
	@Autowired
	private RevenueRepository revenueRepository;

	@Override
	public List<RevenueEntity> getAllRevenue() {
		return revenueRepository.findAll();
	}

	@Override
	public RevenueEntity findRevenueById(LocalDate id) {
		return revenueRepository.findById(id).orElse(null);
	}

	@Override
	public RevenueEntity addRevenue(RevenueEntity revenue) {
		if(revenue!=null) {
			return revenueRepository.save(revenue);
		}
		return null;
	}

	@Override
	public RevenueEntity updateRevenueDay() {
		LocalDate date = LocalDate.now();
		
		RevenueEntity x= findRevenueById(date);
		int total=0;
		for(TicketEntity a : x.getTicketList()) {
			total = total + a.getPrice();
			System.out.println(a.getPrice());
		}
		x.setTotal(total);
		System.out.println("save ne");
		return revenueRepository.save(x);
	}

	@Override
	public int countCar(LocalDate date) {
		int count=0;
		for(TicketEntity tc : revenueRepository.findById(date).get().getTicketList()){
			count++;
		}
		return count;
	}
}
