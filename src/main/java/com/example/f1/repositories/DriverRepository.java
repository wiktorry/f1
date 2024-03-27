package com.example.f1.repositories;

import com.example.f1.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
