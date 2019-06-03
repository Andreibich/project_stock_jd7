package com.htp.domain.hibernate;


import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "shipment")
public class HibernateShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "shipment_date")
    private Date shipmentDate;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_company_id")
    private HibernateCompanies shipmentToCompany;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_catalog_id")
    private HibernateProductCatalog shipmentProduct;

    @Column(name = "shipment_quantity")
    private Double shipmentQuantity;

    @Column(name = "shipment_price")
    private Double shipmentPrice;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "proxy_number")
    private Long proxyNumber;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_user_id")
    private HibernateUser shipmentUser;


    @Column(name = "recepient_employee_name")
    private String recipientEmployeeName;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_code_id")
    private HibernateOperationCodes hibernateOperationCodes;


}
