package com.project.ims.service;

import com.project.ims.model.dto.ExportDTO;
import com.project.ims.model.dto.ProductExportDTO;

import java.util.List;

public interface ExportService {

    ExportDTO saveExport(ExportDTO exportDTO);

    List<ExportDTO> findAllExports();

    ExportDTO findExportById(int exportID);

    List<ProductExportDTO> getProductsByExport(int exportID);
}
