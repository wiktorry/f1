package com.example.f1.team;

import com.example.f1.entity.Driver;
import com.example.f1.entity.Team;
import com.example.f1.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class TeamServiceImplTest {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMVC;
    private List<Team> teams = new ArrayList<>(List.of(
            new Team("Red Bull Racing", "Adrian Newey", List.of(new Driver(), new Driver())),
            new Team("Scuderia Ferrari", "Mattia Binotto", List.of(new Driver(), new Driver())),
            new Team("McLaren", "James Key", List.of(new Driver(), new Driver()))));
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql")
            .withDatabaseName("tests").withUsername("spring").withPassword("spring")
            .withInitScript("test.sql");
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
    @BeforeEach
    @Transactional
    void beforeEach(){
        jdbcTemplate.execute("DELETE FROM drivers");
        jdbcTemplate.execute("DELETE FROM teams");
        jdbcTemplate.execute("ALTER TABLE teams AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE drivers AUTO_INCREMENT = 1");
        for (Team team : teams){
            teamRepository.save(team);
        }
    }
    @Test
    void testAddTeams() throws Exception{
        List<Team> newTeams = new ArrayList<>(List.of(
                new Team("Aston Martin", "Andrew Green", List.of(new Driver(), new Driver())),
                new Team("Alpine F1", "Matt Harman", List.of(new Driver(), new Driver()))));
        for (Team team : newTeams){
            String teamJSON = objectMapper.writeValueAsString(team);
            mockMVC.perform(
                    MockMvcRequestBuilders.post("/f1/team").contentType(MediaType.APPLICATION_JSON)
                    .content(teamJSON)).andExpect(status().isOk());
        }
        Assertions.assertEquals(5, teamRepository.findAll().size());
    }
    @Test
    void testGetAllTeams() throws Exception{
        mockMVC.perform(
                MockMvcRequestBuilders.get("/f1/team")
        ).andExpect(status().isOk());
        Assertions.assertEquals(teams.get(1).getName(), teamRepository.findAll().get(1).getName());
    }
    @Test
    void testGetTeamById() throws Exception{
        mockMVC.perform(
                MockMvcRequestBuilders.get("/f1/team/1")
        ).andExpect(status().isOk());
        Assertions.assertEquals(teams.get(0).getName(), teamRepository.findById(1).get().getName());
    }
    @Test
    void testUpdateTeam() throws Exception{
        Team team = new Team(1, "Mercedes", "James Allison", List.of(new Driver(), new Driver()));
        String teamJSON = objectMapper.writeValueAsString(team);
        mockMVC.perform(
                MockMvcRequestBuilders.put("/f1/team").contentType(MediaType.APPLICATION_JSON)
                        .content(teamJSON)).andExpect(status().isOk());
        Assertions.assertEquals(team.getName(), teamRepository.findById(1).get().getName());
    }
    @Test
    void testDeleteById() throws Exception{
        mockMVC.perform(
                MockMvcRequestBuilders.delete("/f1/team/1")
        ).andExpect(status().isOk());
    }
}