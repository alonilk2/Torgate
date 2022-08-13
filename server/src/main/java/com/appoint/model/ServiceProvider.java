package com.appoint.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "serviceproviders")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @JsonProperty("filename")
    @Column(name = "filename")
    private String Filename;
    private String Firstname;
    private String Lastname;
    private String Phone;
    private String Email;
    @JsonProperty("user")
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-provider")
    private User User;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="serviceProvider", fetch = FetchType.LAZY)
    @JsonProperty("appointments")
    private Set<Appointments> appointments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="serviceProvider", fetch = FetchType.LAZY)
    @JsonManagedReference(value="workdays-providers")
    @JsonProperty("workdays")
    private Set<WorkDays> workDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonProperty("business")
    @JsonBackReference(value = "business-provider")
    private Business business;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty("services")
    @JsonIgnoreProperties(value = {"appointments","business"}, allowSetters = true)
    private Set<Services> Services;
    @JsonIgnore
    public com.appoint.model.User getUser() {
        return User;
    }

    public void setUser(com.appoint.model.User User) {
        this.User = User;
    }

    public Set<Services> getServices() {
        return Services;
    }

    public void setServices(Set<Services> services) {
        Services = services;
    }

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

    public ServiceProvider() {
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }
    public String getFilename() {
        return this.Filename;
    }

    public void setFileName(String filename) {
        this.Filename = filename;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public Set<WorkDays> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<WorkDays> workDays) {
        this.workDays = workDays;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public void addToWorkDays(WorkDays workDays){
        workDays.setServiceProvider(this);
        this.workDays.add(workDays);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
