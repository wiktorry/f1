package com.example.f1.services;

import com.example.f1.entity.Driver;

import java.util.List;

public interface DriverService {
    Driver findById(int id);
    List<Driver> findAll();
    Driver create(Driver driver);
    Driver update(Driver driver);
    void deleteById(int id);
}
