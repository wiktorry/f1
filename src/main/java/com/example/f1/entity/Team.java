package com.example.f1.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="teamId")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="technicalDirector")
    private String technicalDirector;
    @OneToMany(mappedBy = "team", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Driver> drivers;
    public void addDriver(Driver driver){
        if(drivers == null){
            drivers = new ArrayList<Driver>();
        }
        drivers.add(driver);
    }
    public Team(String name, String technicalDirector, List<Driver> drivers) {
        this.name = name;
        this.technicalDirector = technicalDirector;
        this.drivers = drivers;
    }
}
