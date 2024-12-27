package com.project.ims.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public abstract class BaseTransaction {


    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "total_money", nullable = false)
    private Double totalMoney;

    // Quan hệ ManyToOne sẽ được cụ thể hóa ở các lớp con (Export, Import)
}
