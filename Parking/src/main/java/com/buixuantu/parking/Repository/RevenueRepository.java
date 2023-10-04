package com.buixuantu.parking.Repository;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buixuantu.parking.entity.RevenueEntity;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueEntity, LocalDate>{
}
