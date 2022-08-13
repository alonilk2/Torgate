package com.appoint.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @JsonProperty(value = "day")
    @Temporal(TemporalType.DATE)
    private Date day;
    @JsonProperty(value = "start_hour")
    private String start_hour;
    @JsonProperty(value = "end_hour")
    private String end_hour;
    @JsonProperty(value = "customer")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = "appointments", allowSetters = true)
    private Customer customer;
    @JsonProperty(value = "serviceProvider")
    @ManyToOne
    @JsonIgnoreProperties(value = {"appointments", "services", "workdays"}, allowSetters = true)
    @JoinColumn(name = "provider_id")
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties(value = {"appointments"}, allowSetters = true)
    private Services service;

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDay() {
        return this.day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
