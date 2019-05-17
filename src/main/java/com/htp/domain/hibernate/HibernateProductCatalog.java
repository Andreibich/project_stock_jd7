package com.htp.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "product_catalog")
public class HibernateProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_unit")
    private String productUnit;

    @JsonBackReference
    @OneToMany(mappedBy = "shipmentProduct", fetch = FetchType.LAZY)
    private List<HibernateShipment> hibernateShipments = Collections.emptyList();

    @JsonBackReference
    @OneToMany(mappedBy = "receiptProduct", fetch = FetchType.LAZY)
    private List<HibernateReceipt> hibernateReceipt = Collections.emptyList();
}
