package com.project.ims.repository;

import com.project.ims.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
    List<Partner> findByNameContaining(String name);
}
