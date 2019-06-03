package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateOperationCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SpringDataOperationCodesDao extends JpaRepository<HibernateOperationCodes, Long> {

    HibernateOperationCodes findByPurpose(String purpose);
}
