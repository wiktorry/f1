package com.example.f1.services;

import com.example.f1.entity.Driver;
import com.example.f1.exceptions.NotFoundException;
import com.example.f1.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver findById(int id) {
        return driverRepository.findById(id).orElseThrow(() -> new NotFoundException("Driver doesn't exist"));
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver create(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver update(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public void deleteById(int id) {
        Driver driver = driverRepository.findById(id).orElseThrow(() -> new NotFoundException("Driver doesn't exist"));
        List<Driver> drivers = driver.getTeam().getDrivers();
        drivers.remove(driver);
        driver.getTeam().setDrivers(drivers);
        driverRepository.deleteById(id);
    }
}
