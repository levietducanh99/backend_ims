package com.project.ims.controller;

import com.project.ims.model.dto.ImportDTO;
import com.project.ims.service.ExportService;
import com.project.ims.service.ImportService;

import java.time.LocalDateTime;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ImportService importService;
    @Autowired
    private ExportService exportService;

    @GetMapping("/{type}/{id}")
    public ResponseEntity<?> getTransactionDetails(@PathVariable String type, @PathVariable int id) {
        if ("import".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(importService.getImportDetails(id));
        } else if ("export".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(exportService.getExportDetails(id));
        } else {
            return ResponseEntity.badRequest().body("Invalid transaction type");
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<?> filterTransactions(
        @RequestParam String type,  
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(required = false) Integer supplierId,
        @RequestParam(required = false) Integer partnerId,
        @RequestParam(required = false) Integer minProductQuantity,
        @RequestParam(required = false) Integer maxProductQuantity) {
    	 // Set default to the current year if startDate or endDate is null
        if (startDate == null) {
            startDate = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0);
        }
        
        if (endDate == null) {
            endDate = LocalDateTime.of(2024, Month.DECEMBER, 31, 23, 59);
        }
        if ("import".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(importService.filterImports(startDate, endDate, supplierId, minProductQuantity, maxProductQuantity));
        } else if ("export".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(exportService.filterExports(startDate, endDate, partnerId, minProductQuantity, maxProductQuantity));
        } else {
            return ResponseEntity.badRequest().body("Invalid transaction type");
        }
    }

}