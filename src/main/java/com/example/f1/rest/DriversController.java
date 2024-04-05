package com.example.f1.rest;

import com.example.f1.entity.Driver;
import com.example.f1.services.DriverServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("f1/driver")
public class DriversController {
    private final DriverServiceImpl driverService;

    public DriversController(DriverServiceImpl driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{driverId}")
    public Driver getDriverById(@PathVariable int driverId) {
        return driverService.findById(driverId);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.findAll();
    }

    @PostMapping
    public Driver addDriver(@RequestBody Driver driver) {
        driver.getTeam().addDriver(driver);
        return driverService.create(driver);
    }

    @PutMapping
    public Driver updateDriver(@RequestBody Driver driver) {
        return driverService.update(driver);
    }

    @DeleteMapping("/{driverId}")
    public void deleteUser(@PathVariable int driverId) {
        driverService.deleteById(driverId);
    }
}
