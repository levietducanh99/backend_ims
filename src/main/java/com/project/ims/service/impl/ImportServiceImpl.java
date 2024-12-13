package com.project.ims.service.impl;

import com.project.ims.model.dto.ImportDTO;
import com.project.ims.model.dto.ProductImportDTO;
import com.project.ims.model.entity.Import;
import com.project.ims.model.entity.ProductImport;
import com.project.ims.model.entity.Supplier;
import com.project.ims.model.entity.Product;
import com.project.ims.repository.ImportRepository;
import com.project.ims.repository.ProductImportRepository;
import com.project.ims.repository.SupplierRepository;
import com.project.ims.repository.ProductRepository;
import com.project.ims.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ImportRepository importRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImportRepository productImportRepository;

    @Override
    @Transactional
    public ImportDTO saveImport(ImportDTO importDTO) {
        // Tạo đối tượng Import và gán dữ liệu
        Import importEntity = new Import();
        importEntity.setTotalQuantity(importDTO.getTotalQuantity());
        importEntity.setTotalMoney(importDTO.getTotalMoney()); // totalMoney kiểu int

        // Tìm và thiết lập Supplier
        Supplier supplier = supplierRepository.findById(Integer.parseInt(importDTO.getSupplierID()))
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        importEntity.setSupplier(supplier);

        // Lưu importEntity để lấy ID sau khi persist
        importEntity = importRepository.save(importEntity);

        // Lưu danh sách ProductImport
        List<ProductImport> productImports = new ArrayList<>();
        for (int i = 0; i < importDTO.getProductIDs().size(); i++) {
            String productId = importDTO.getProductIDs().get(i);
            int quantity = Integer.parseInt(importDTO.getQuantities().get(i));

            Product product = productRepository.findById(Integer.parseInt(productId))
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductImport productImport = new ProductImport();
            productImport.setProductEntity(product);
            productImport.setQuantity(quantity);
            productImport.setImportEntity(importEntity);
            productImport.setTotalMoney(quantity * product.getPrice()); // Tổng tiền từng sản phẩm
            productImports.add(productImport);
        }
        productImportRepository.saveAll(productImports);

        // Chuyển đổi lại ImportEntity sang DTO để trả về
        ImportDTO result = new ImportDTO();
        result.setImportID(importEntity.getImportID());
        result.setQuantities(importDTO.getQuantities());
        result.setTotalMoney(importDTO.getTotalMoney());
        result.setTotalQuantity(importDTO.getTotalQuantity());
        result.setSupplierID(importDTO.getSupplierID());
        result.setProductIDs(importDTO.getProductIDs());

        return result;
    }



}