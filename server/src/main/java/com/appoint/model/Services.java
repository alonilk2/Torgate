package com.appoint.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
//    @JsonBackReference(value="business-services"
    @JsonIgnoreProperties(value = {"services", "serviceProviders"}, allowSetters = true)
    private Business business;
    @ManyToMany(mappedBy = "Services", fetch = FetchType.LAZY)
    @JsonBackReference(value="provider-services")
    private Set<ServiceProvider> serviceProviderSet;

    @JsonProperty("img")
    @Column(name = "img")
    private String img;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="service", fetch = FetchType.LAZY)
    @JsonProperty("appointments")
    private Set<Appointments> appointments;
    private Integer cost;
    @JsonProperty("duration")
    private Integer duration;

    public Set<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointments> appointments) {
        this.appointments = appointments;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<ServiceProvider> getServiceProviderSet() {
        return serviceProviderSet;
    }

    public void setServiceProviderSet(Set<ServiceProvider> serviceProviderSet) {
        this.serviceProviderSet = serviceProviderSet;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Services() {
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }



}
