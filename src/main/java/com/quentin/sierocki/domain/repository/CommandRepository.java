package com.quentin.sierocki.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.domain.entity.CommandDAO;

@Repository
public interface CommandRepository extends JpaRepository<CommandDAO, Integer> {
}