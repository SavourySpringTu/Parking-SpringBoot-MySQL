package com.buixuantu.parking.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="customer")
public class CustomerEntity {
    @Id
    private String email;
    private String name;
    private String password;

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<TicketEntity> ticketList;


    public CustomerEntity() {
        super();
    }

    public CustomerEntity(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TicketEntity> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketEntity> ticketList) {
        this.ticketList = ticketList;
    }
}
