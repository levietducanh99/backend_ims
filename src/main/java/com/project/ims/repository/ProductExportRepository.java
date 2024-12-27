package com.project.ims.repository;

import com.project.ims.model.entity.ProductExport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductExportRepository extends JpaRepository<ProductExport, Integer> {
    // Các phương thức tùy chỉnh có thể được thêm vào đây nếu cần thiết
}
