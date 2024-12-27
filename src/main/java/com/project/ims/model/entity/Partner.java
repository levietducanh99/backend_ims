package com.project.ims.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "partner")
public class Partner extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partnerID;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Export> exports;
}
