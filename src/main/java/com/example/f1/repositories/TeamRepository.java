package com.example.f1.repositories;

import com.example.f1.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t FROM Team t JOIN FETCH t.drivers")
    List<Team> findAllTeamsWithDrivers();
}
