package com.quentin.sierocki.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.domain.entity.ProductTypeDAO;
import com.quentin.sierocki.domain.entity.UserDAO;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeDAO, Integer> {
	ProductTypeDAO findByName(String name);
}
