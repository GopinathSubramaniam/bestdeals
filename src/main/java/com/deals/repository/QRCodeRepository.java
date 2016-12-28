package com.deals.repository;

import com.deals.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface QRCodeRepository extends JpaRepository<QRCode, Long>{

}
