package com.quentin.sierocki.legume.back.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.legume.back.domain.entity.UserDAO;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {
	UserDAO findByUsername(String username);
}
