package com.appoint.repository;

import com.appoint.model.Appointments;
import com.appoint.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;


public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {
    public Set<Appointments> findAllByDay(Date day);
    public Set<Appointments> findAllByServiceProvider(ServiceProvider serviceProvider);
    @Query("select e from Appointments e where year(e.day) = ?1 and month(e.day) = ?2 and e.serviceProvider.business.id = ?3")
    public List<Appointments> findAllByMonthAndYear(int year, int month, int id);
    public List<Appointments> findAllByDayBetween(Date start, Date end);

}
