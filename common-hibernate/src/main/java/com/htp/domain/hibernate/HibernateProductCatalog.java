package com.htp.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "shipmentProduct"/*, fetch = FetchType.EAGER*/)
    private List<HibernateShipment> hibernateShipments = Collections.emptyList();

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "receiptProduct"/*, fetch = FetchType.EAGER*/)
    private List<HibernateReceipt> hibernateReceipt = Collections.emptyList();
}
