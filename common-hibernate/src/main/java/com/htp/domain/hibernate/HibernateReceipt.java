package com.htp.domain.hibernate;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "receipt")
public class HibernateReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    @Column(name = "receipt_date")
    private Date receiptDate;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private HibernateCompanies receiptFromCompany;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_catalog_id")
    private HibernateProductCatalog receiptProduct;

    @Column(name = "receipt_quantity")
    private Double receiptQuantity;

    @Column(name = "receipt_price")
    private Double receiptPrice;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_user_id")
    private HibernateUser receiptUser;


}
