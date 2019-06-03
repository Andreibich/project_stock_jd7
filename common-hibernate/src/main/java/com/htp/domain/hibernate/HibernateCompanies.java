package com.htp.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Collections;

@Data
@Entity
@Table(name = "companies")
public class HibernateCompanies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    @Size(max = 50)
    private String companyName;

    @Column(name = "city")
    @Size(max = 50)
    private String city;

    @Column(name = "address")
    @Size(max = 50)
    private String address;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "receiptFromCompany"/*, fetch = FetchType.EAGER*/)
    private List<HibernateReceipt> hibernateReceipts = Collections.emptyList();

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "shipmentToCompany"/*, fetch = FetchType.EAGER*/)
    private List<HibernateShipment> hibernateShipments = Collections.emptyList();


}
