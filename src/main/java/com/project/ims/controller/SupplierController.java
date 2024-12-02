package com.project.ims.controller;

import com.project.ims.model.dto.SupplierDTOForAddProduct;
import com.project.ims.model.dto.SupplierDTOForCreate;
import com.project.ims.model.dto.SupplierDTOForShow;
import com.project.ims.model.entity.Product;
import com.project.ims.model.entity.Supplier;
import com.project.ims.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/get-all")
    public List<SupplierDTOForShow> getAllSuppliers() {
        return supplierService.findAllDTO();
    }

    @GetMapping("/search")
    public List<SupplierDTOForShow> searchSuppliersByName(@RequestParam String name) {
        return supplierService.findByNameContainingDTO(name);
    }
    
    @GetMapping("/{supplierId}/products")
    public List<Product> getSupplierProducts(@PathVariable int supplierId) {
        return supplierService.getProductsBySupplier(supplierId);
    }
    @PostMapping("/{supplierId}/products/{productId}")
    public ResponseEntity<String> addProductToSupplier(@PathVariable int supplierId, @PathVariable int productId) {
        try {
            supplierService.addProductToSupplier(supplierId, productId);
            return ResponseEntity.ok("Product added to supplier successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/add-product")
    public ResponseEntity<String> addProductToSupplier(@RequestBody SupplierDTOForAddProduct request) {
        try {
            supplierService.addProductToSupplier(request);
            return ResponseEntity.ok("Product added to supplier successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 // Phương thức để thêm nhà cung cấp
    @PostMapping
    public ResponseEntity<String> addSupplier(@RequestBody SupplierDTOForCreate supplierDTO) {
        // Chuyển đổi SupplierDTO thành đối tượng Supplier
        Supplier newSupplier = new Supplier();
        newSupplier.setName(supplierDTO.getName());
        newSupplier.setContactNumber(supplierDTO.getContactNumber());
        newSupplier.setAddress(supplierDTO.getAddress());

        // Gọi Service để lưu nhà cung cấp mới vào cơ sở dữ liệu
        boolean isAdded = supplierService.addSupplier(newSupplier);

        if (isAdded) {
            return ResponseEntity.ok("Nhà cung cấp đã được thêm thành công");
        } else {
            return ResponseEntity.status(400).body("Lỗi khi thêm nhà cung cấp");
        }
    }
}