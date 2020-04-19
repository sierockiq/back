package com.quentin.sierocki.legume.back.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;

@Repository
public interface ProductRepository extends JpaRepository<ProductDAO, Long> {

}
