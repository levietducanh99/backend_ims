package com.project.ims.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ims.model.entity.ProductImport;

@Repository
public interface ProductImportRepository extends JpaRepository<ProductImport, Integer> {
}
