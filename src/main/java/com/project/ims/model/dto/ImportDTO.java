package com.project.ims.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ImportDTO {
	private int importID;
    private List<String> quantities;       // Danh sách số lượng của từng sản phẩm
    private Double totalMoney;        // Tổng số tiền, dùng BigDecimal để lưu trữ giá trị tiền tệ
    private int totalQuantity;            // Tổng số lượng sản phẩm
    private String supplierID;            // ID nhà cung cấp (kiểu String để phù hợp với JSON)
    private List<String> productIDs;      // Danh sách ID sản phẩm
}
