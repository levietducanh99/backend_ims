package com.project.ims.service;

import com.project.ims.model.dto.SupplierDTOForAddProduct;
import com.project.ims.model.dto.SupplierDTOForShow;
import com.project.ims.model.entity.Product;
import com.project.ims.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<SupplierDTOForShow> findAllDTO();
    List<SupplierDTOForShow> findByNameContainingDTO(String name);
    List<Product> getProductsBySupplier(int supplierId);
    void addProductToSupplier(int supplierId, int productId);
    void addProductToSupplier(SupplierDTOForAddProduct request);
	boolean addSupplier(Supplier supplier);
}