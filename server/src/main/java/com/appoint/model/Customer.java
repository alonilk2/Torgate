package com.appoint.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;
    @JsonProperty("firstname")
    private String Firstname;
    @JsonProperty("lastname")
    private String Lastname;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")

    private String Email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="customer", fetch = FetchType.LAZY)
    @JsonProperty("appointments")
    @JsonIgnoreProperties(value="customer", allowSetters = true)
    private Set<Appointments> appointments;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonProperty("business")
    private Set<Business> business;
    public Customer() {
    }

    public Set<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointments> appointments) {
        this.appointments = appointments;
    }

    public Set<Business> getBusiness() {
        return business;
    }

    public void setBusiness(Set<Business> business) {
        this.business = business;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
