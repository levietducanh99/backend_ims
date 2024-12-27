package com.project.ims.controller;

import com.project.ims.model.dto.InventoryProductDTO;
import com.project.ims.model.dto.TopSupplierDTO;
import com.project.ims.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    @GetMapping("/top-inventory")
    public ResponseEntity<List<InventoryProductDTO>> getTopInventory(
            @RequestParam String type,
            @RequestParam(defaultValue = "5") int limit) {
        List<InventoryProductDTO> products = overviewService.getTopInventoryProducts(type, limit);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-suppliers-by-products")
    public ResponseEntity<List<TopSupplierDTO>> getTopSuppliersByProducts(
            @RequestParam(defaultValue = "5") int limit) {
        List<TopSupplierDTO> suppliers = overviewService.getTopSuppliersByProducts(limit);
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/top-suppliers-by-imports")
    public ResponseEntity<List<TopSupplierDTO>> getTopSuppliersByImports(
            @RequestParam(defaultValue = "5") int limit) {
        List<TopSupplierDTO> suppliers = overviewService.getTopSuppliersByImports(limit);
        return ResponseEntity.ok(suppliers);
    }
}