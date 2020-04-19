package com.quentin.sierocki.legume.back.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.legume.back.domain.entity.ProductTypeDAO;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeDAO, Long> {
	ProductTypeDAO findByName(String name);
}
