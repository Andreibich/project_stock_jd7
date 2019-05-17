package com.htp.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "operation_codes")
public class HibernateOperationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_code_id")
    private Long operationCodeId;

    @Column(name = "purpose")
    private String purpose;

    @JsonBackReference
    @OneToMany(mappedBy = "hibernateOperationCodes", fetch = FetchType.EAGER)
    private List<HibernateShipment> hibernateShipment = Collections.emptyList();

}
