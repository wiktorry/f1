package com.example.f1.services;

import com.example.f1.entity.Team;

import java.util.List;

public interface TeamService {
    Team findById(int id);

    List<Team> findAll();

    Team create(Team team);

    Team update(Team team);

    void deleteById(int id);
}
