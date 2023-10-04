package com.buixuantu.parking.ServiceImpl;

import com.buixuantu.parking.Repository.CustomerRepository;
import com.buixuantu.parking.Service.CustomerService;
import com.buixuantu.parking.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity addCustomer(String email, String name,String password) {
        CustomerEntity cs = new CustomerEntity(email,name,password);
        return customerRepository.save(cs);
    }

    @Override
    public void updateCustomer(String email, String name,String password) {
        CustomerEntity cs = customerRepository.findById(email).orElse(null);
        cs.setName(name);
        cs.setPassword(password);
        customerRepository.save(cs);
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        customerRepository.deleteById(email);
    }

    @Override
    public Page<CustomerEntity> findAllPage(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public CustomerEntity login(String user, String password) {
        for(CustomerEntity cs : customerRepository.findAll()){
            if(user.equals(cs.getEmail())==true && password.equals(cs.getPassword())==true){
                return cs;
            }
        }
        System.out.println("null ne");
        return null;
    }

    @Override
    public CustomerEntity findCustomerByEmail(String email) {
        for(CustomerEntity cs : customerRepository.findAll()){
            if(email.equals(cs.getEmail())==true){
                return cs;
            }
        }
        return null;
    }
}
