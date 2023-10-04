package com.buixuantu.parking.Service;

import java.time.LocalDate;
import java.util.List;

import com.buixuantu.parking.entity.RevenueEntity;
import org.springframework.cglib.core.Local;

public interface RevenueService {
	List<RevenueEntity> getAllRevenue();
	RevenueEntity findRevenueById(LocalDate id);
	RevenueEntity addRevenue(RevenueEntity revenue);
	RevenueEntity updateRevenueDay();
	int countCar(LocalDate date);
}
