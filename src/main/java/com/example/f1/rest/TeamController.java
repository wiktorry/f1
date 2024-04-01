package com.example.f1.rest;

import com.example.f1.entity.Team;
import com.example.f1.services.DriverServiceImpl;
import com.example.f1.services.TeamServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("f1/team")
public class TeamController {
    private final TeamServiceImpl teamService;
    public TeamController(TeamServiceImpl teamService){
        this.teamService = teamService;
    }
    @GetMapping("/{teamId}")
    private Team getTeamById(@PathVariable int teamId){
        return teamService.findById(teamId);
    }
    @GetMapping
    private List<Team> getAllTeams(){
        return teamService.findAll();
    }
    @PostMapping
    private Team addTeam(@RequestBody Team team){
        return teamService.create(team);
    }
    @PutMapping
    private Team updateTeam(@RequestBody Team team){
        return teamService.update(team);
    }
    @DeleteMapping("/{teamId}")
    private void deleteTeam(@PathVariable int teamId){
        teamService.deleteById(teamId);
    }
}
