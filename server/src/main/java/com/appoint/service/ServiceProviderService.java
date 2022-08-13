package com.appoint.service;

import com.appoint.model.ServiceProvider;
import com.appoint.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceProviderService {
    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    public List<ServiceProvider> list() {
        return serviceProviderRepository.findAll();
    }

    public ServiceProvider save(ServiceProvider serviceProvider) {
        serviceProvider.getWorkDays().forEach(workDays -> workDays.setServiceProvider(serviceProvider));
        serviceProviderRepository.save(serviceProvider);
        return serviceProvider;
    }

    public ServiceProvider get(Integer id) {
        return serviceProviderRepository.findById(id).get();
    }

    public void delete(Integer id) {
        serviceProviderRepository.deleteById(id);
    }
}
