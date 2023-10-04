package com.buixuantu.parking.Service;

import com.buixuantu.parking.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAllCustomer();
    CustomerEntity addCustomer(String email,String name,String password);
    void updateCustomer(String email,String name,String password);
    void deleteCustomerByEmail(String email);
    Page<CustomerEntity> findAllPage(Pageable pageable);
    CustomerEntity login(String user,String passowrd);

    CustomerEntity findCustomerByEmail(String email);
}
