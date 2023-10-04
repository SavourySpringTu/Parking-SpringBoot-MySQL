package com.buixuantu.parking.Service;

import com.buixuantu.parking.entity.TicketEntity;
import com.buixuantu.parking.entity.WarehouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseService {
    List<WarehouseEntity> getAllWarehouse();
    WarehouseEntity findWarehouseById(long id);
    WarehouseEntity findWarehouseByTicket(String id);
    WarehouseEntity addWarehouse(TicketEntity ticket);
    void updateWarehouseByTicket(String ticket);
    void deleteWarehouseByTicket(String ticket);
    void exportWarehouseTicket(String ticket);
    Page<WarehouseEntity> findAllPage(Pageable pageable);
    void checkTimeTicket();
}
