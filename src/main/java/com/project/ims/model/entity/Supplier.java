package com.project.ims.model.entity;

/*
CREATE TABLE Supplier (
    supplierID INT PRIMARY KEY,
    name VARCHAR(100),
    contactNumber VARCHAR(20),
    address VARCHAR(255)
);
 */

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int supplierID;

    @Column(name= "name", nullable = false)
    private String name;

    @Column(name = "contactNumber", nullable = false)
    private String contactNumber;

    @Column(name = "address", nullable = false)
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Import> imports;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "SupplierProduct",
            joinColumns = @JoinColumn(name = "supplierID"),
            inverseJoinColumns = @JoinColumn(name = "productID")
    )
    private List<Product> products;
}
