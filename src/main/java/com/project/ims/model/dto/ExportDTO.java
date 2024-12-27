package com.project.ims.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExportDTO {
    private int exportID;
    private int totalQuantity;
    private Double totalMoney;
    private String partnerID;
    private List<String> productIDs; // Các sản phẩm liên quan
    private List<String> quantities; // Số lượng của từng sản phẩm
}
