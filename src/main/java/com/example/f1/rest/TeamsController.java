package com.example.f1.rest;

import com.example.f1.entity.Team;
import com.example.f1.services.TeamServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("f1/team")
public class TeamsController {
    private final TeamServiceImpl teamService;

    public TeamsController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{teamId}")
    public Team getTeamById(@PathVariable int teamId) {
        return teamService.findById(teamId);
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.findAll();
    }

    @PostMapping
    public Team addTeam(@RequestBody Team team) {
        return teamService.create(team);
    }

    @PutMapping
    public Team updateTeam(@RequestBody Team team) {
        return teamService.update(team);
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable int teamId) {
        teamService.deleteById(teamId);
    }
}
