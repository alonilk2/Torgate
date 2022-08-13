package com.appoint.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "workdays")
public class WorkDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @JsonProperty("day")
    private Integer dayId;
    @JsonProperty("starttime")
    private String startHour;
    @JsonProperty("endtime")
    private String endHour;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    @JsonBackReference(value="workdays-providers")
    private ServiceProvider serviceProvider;
    @JsonProperty("breakStarttime")
    private String breakStartTime;
    @JsonProperty("breakEndtime")
    private String breakEndTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonBackReference
    private Business business;

    public WorkDays(Integer id, Integer dayId, String startHour, String endHour, ServiceProvider serviceProvider, String breakStartTime, String breakEndTime, Business business) {
        this.id = id;
        this.dayId = dayId;
        this.startHour = startHour;
        this.endHour = endHour;
        this.serviceProvider = serviceProvider;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.business = business;
    }

    public String getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(String breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public String getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(String breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public WorkDays(Integer dayId, String startHour, String endHour, ServiceProvider serviceProvider) {
        this.dayId = dayId;
        this.startHour = startHour;
        this.endHour = endHour;
        this.serviceProvider = serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public WorkDays() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
