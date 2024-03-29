package com.example.f1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="teamId")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="technicalDirector")
    private String technicalDirector;
    @OneToMany(mappedBy = "driver", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Driver> drivers;
    public Team(String name, String technicalDirector, List<Driver> drivers){
        this.name = name;
        this.technicalDirector = technicalDirector;
        this.drivers = drivers;
        for (Driver driver : drivers){
            driver.setTeam(this);
        }
    }
}
