package com.project.ims.service;

import com.project.ims.model.dto.InventoryProductDTO;
import com.project.ims.model.dto.TopSupplierDTO;
import java.util.List;

public interface OverviewService {
    List<InventoryProductDTO> getTopInventoryProducts(String type, int limit);
    List<TopSupplierDTO> getTopSuppliersByProducts(int limit);
    List<TopSupplierDTO> getTopSuppliersByImports(int limit);
}