package com.quentin.sierocki.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.domain.entity.CommandDAO;
import com.quentin.sierocki.domain.entity.CommandProductDAO;

@Repository
public interface CommandProductRepository extends JpaRepository<CommandProductDAO, Integer> {
}
