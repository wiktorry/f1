package com.example.f1.team;

import com.example.f1.entity.Driver;
import com.example.f1.entity.Team;
import com.example.f1.exceptions.BadRequestException;
import com.example.f1.repositories.TeamRepository;
import com.example.f1.services.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {
    private TeamServiceImpl teamService;
    @Mock
    private TeamRepository teamRepository;
    @BeforeEach
    void setUp() {
        teamService = new TeamServiceImpl(teamRepository);
    }
    @Test
    @Disabled
    void findById() {

    }

    @Test
    void canFindAll() {
        teamService.findAll();
        verify(teamRepository).findAll();
    }

    @Test
    void canCreate() {
        Team team = new Team(
                "Red Bull Racing",
                "Adrian Newey",
                List.of(new Driver("Max", "Verstappen", "Netherlands"),
                        new Driver("Sergio", "Perez", "Mexico"))
        );
        teamService.create(team);
        ArgumentCaptor<Team> teamArgumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(teamArgumentCaptor.capture());
        Team capturedTeam = teamArgumentCaptor.getValue();
        assertEquals(capturedTeam, team);
    }

    @Test
    void willThrowWhenNotTwoDrivers() {
        Team team = new Team(
                "Red Bull Racing",
                "Adrian Newey",
                List.of(new Driver("Max", "Verstappen", "Netherlands"))
        );
        assertThatThrownBy(() -> teamService.create(team))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Team must have exactly two drivers");
        verify(teamRepository, never()).save(any());
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void deleteById() {

    }
}