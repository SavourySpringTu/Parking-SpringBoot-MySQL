package com.buixuantu.parking.ServiceImpl;

import com.buixuantu.parking.Repository.TicketRepository;
import com.buixuantu.parking.Repository.WarehouseRepository;
import com.buixuantu.parking.Service.TicketService;
import com.buixuantu.parking.Service.WarehouseService;
import com.buixuantu.parking.entity.TicketEntity;
import com.buixuantu.parking.entity.WarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private TicketService ticketService;

    @Override
    public List<WarehouseEntity> getAllWarehouse() {
        return warehouseRepository.findAll();
    }

    @Override
    public WarehouseEntity findWarehouseById(long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Override
    public WarehouseEntity findWarehouseByTicket(String id) {
        for(WarehouseEntity w : warehouseRepository.findAll()){
            if(w.getTicket().getId().equals(id)==true)
            {
                return w;
            }
        }
        return null;
    }

    @Override
    public WarehouseEntity addWarehouse(TicketEntity ticket) {
        return null;
    }

    @Override
    public void updateWarehouseByTicket(String ticket) {

    }

    @Override
    public void deleteWarehouseByTicket(String ticket) {

    }

    @Override
    public void exportWarehouseTicket(String ticket) {
        WarehouseEntity wh = findWarehouseByTicket(ticket);
        wh.setStatus(true);
        warehouseRepository.save(wh);
    }

    @Override
    public Page<WarehouseEntity> findAllPage(Pageable pageable) {
        return warehouseRepository.findAll(pageable);
    }

    @Override
    public void checkTimeTicket() {
        LocalDate date = LocalDate.now();
        for(TicketEntity tc : ticketService.getAllTicket()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String tu=(tc.getTime()).format(formatter);
            LocalDateTime time = LocalDateTime.parse(tu, formatter);
            if(tc.isStatus()==false){
                System.out.println("abc");
                System.out.println(ChronoUnit.DAYS.between(date,time));
                if((tc.isTicket_type()==false && ChronoUnit.DAYS.between(date,time)<-1) || (tc.isTicket_type()==true && ChronoUnit.DAYS.between(date,time)<-30)){
                    WarehouseEntity wh = new WarehouseEntity(date,false,tc);
                    ticketService.exportTicket(tc.getId());
                    warehouseRepository.save(wh);
                    return;
                }
            }
        }
    }
}
