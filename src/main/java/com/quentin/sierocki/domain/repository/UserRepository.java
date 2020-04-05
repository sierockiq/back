package com.quentin.sierocki.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.domain.entity.UserDAO;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {
	UserDAO findByUsername(String username);
}
