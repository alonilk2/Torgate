package com.appoint.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String address;
    private String phone1;
    private String phone2;
    private String img;
    private String headerImg;
    private String subtitle;
    private String aboutus;
    private String pageColor;
    private String email;
    private String website;
    @Column(columnDefinition = "TEXT")
    private String galleryLinks;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="business", fetch = FetchType.LAZY)
//    @JsonManagedReference(value="business-services")
    @JsonProperty("services")
    @JsonIgnoreProperties(value = {"business", "appointments", "serviceProviderSet"}, allowSetters = true, allowGetters = true)
    private Set<Services> services;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="business", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonProperty("workdays")
    private Set<WorkDays> workDays;
    @ManyToMany(mappedBy="business", fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-business")
    @JsonProperty("customers")
    private Set<Customer> customers;
    @JsonBackReference(value = "business-user")
    @OneToMany(mappedBy = "business")
    private Set<User> users;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy="business", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "business-provider")
    private Set<ServiceProvider> serviceProviders;
    public String getGallery() {
        return galleryLinks;
    }

    public void setGallery(String gallery) {
        this.galleryLinks = gallery;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPageColor() {
        return pageColor;
    }

    public void setPageColor(String pageColor) {
        this.pageColor = pageColor;
    }
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public Set<WorkDays> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<WorkDays> workDays) {
        this.workDays = workDays;
    }

    public Set<Services> getServices() {
        return services;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Business() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
