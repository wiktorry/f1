package com.example.f1.services;

import com.example.f1.entity.Driver;
import com.example.f1.entity.Team;
import com.example.f1.exceptions.BadRequestException;
import com.example.f1.exceptions.NotFoundException;
import com.example.f1.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamServiceImpl implements  TeamService{
    private final TeamRepository teamRepository;
    public TeamServiceImpl(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }
    @Override
    public Team findById(int id) {
        return teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team doesn't exist"));
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team create(Team team) {
        List<Driver> drivers = team.getDrivers();
        if(drivers != null){
            for (Driver driver : drivers){
                driver.setTeam(team);
            }
        }
        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        List<Driver> drivers = team.getDrivers();
        if (drivers.size() != 2){
            throw new BadRequestException("Team must have exactly two drivers");
        }
        return teamRepository.save(team);
    }

    @Override
    public void deleteById(int id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team doesn't exist"));
        for (Driver driver : team.getDrivers()){
            driver.setTeam(null);
        }
        teamRepository.deleteById(id);
    }
}
