package com.project.ims.service.impl;

import com.project.ims.model.dto.ProductDTO;
import com.project.ims.model.dto.SupplierDTO;
import com.project.ims.model.dto.SupplierDTOForAddProduct;
import com.project.ims.model.dto.SupplierDTOForShow;
import com.project.ims.model.dto.SupplierProductDTOForShow;

import com.project.ims.model.entity.Supplier;
import com.project.ims.model.entity.Product;
import com.project.ims.repository.ProductRepository;
import com.project.ims.repository.SupplierRepository;
import com.project.ims.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<SupplierDTOForShow> findAllDTO() {
        return supplierRepository.findAll().stream()
                .map(SupplierDTOForShow::fromEntity)
                .collect(Collectors.toList());
    }
@Override
//Phương thức để thêm nhà cung cấp vào cơ sở dữ liệu
public boolean addSupplier(Supplier supplier) {
    try {
        supplierRepository.save(supplier);  // Lưu nhà cung cấp vào cơ sở dữ liệu
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    @Override
    public List<SupplierDTOForShow> findByNameContainingDTO(String name) {
        return supplierRepository.findByNameContaining(name).stream()
                .map(SupplierDTOForShow::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierProductDTOForShow> getProductsBySupplier(int supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        return supplier.getProducts().stream()
                .map(product -> new SupplierProductDTOForShow(
                        product.getProductID(),
                        product.getProductName(),
                        product.getCategory(),
                        product.getPrice()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void addProductToSupplier(int supplierId, int productId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Kiểm tra xem sản phẩm đã có trong danh sách của nhà cung cấp chưa
        if (!supplier.getProducts().contains(product)) {
            supplier.getProducts().add(product);
            supplierRepository.save(supplier); // Lưu cập nhật nhà cung cấp
        } else {
            throw new RuntimeException("Product already associated with this supplier.");
        }
    }
    public void addProductToSupplier(SupplierDTOForAddProduct request) {
        int supplierId = request.getSupplierId();
        int productId = request.getProductId();

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Kiểm tra xem sản phẩm đã có trong danh sách của nhà cung cấp chưa
        if (!supplier.getProducts().contains(product)) {
            supplier.getProducts().add(product);
            supplierRepository.save(supplier); // Lưu cập nhật nhà cung cấp
        } else {
            throw new RuntimeException("Product already associated with this supplier.");
        }
    }
    @Override
    public List<SupplierDTO> findAllSimpleDTO() {
        return supplierRepository.findAll().stream()
                .map(supplier -> {
                    SupplierDTO dto = new SupplierDTO();
                    dto.setSupplierID(supplier.getSupplierID());
                    dto.setName(supplier.getName());   
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDTO> getProductsBySupplierSimple(String supplierName) {
    	Supplier supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return supplier.getProducts().stream()
                .map(product -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setProductID(product.getProductID());
                    dto.setProductName(product.getProductName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
	public Optional<Supplier> findbyidSupplier(int supplierID)
    {
    	return supplierRepository.findById(supplierID);
    }
    
}