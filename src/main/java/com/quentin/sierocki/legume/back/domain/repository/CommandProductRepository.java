package com.quentin.sierocki.legume.back.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentin.sierocki.legume.back.domain.entity.CommandProductDAO;

@Repository
public interface CommandProductRepository extends JpaRepository<CommandProductDAO, Long> {

	List<CommandProductDAO> findByProduct_Id(final long id);

}
