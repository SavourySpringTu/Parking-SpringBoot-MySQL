package com.buixuantu.parking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buixuantu.parking.entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity,String>{

}
