package com.deals.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.QRCode;

@Transactional
public interface QRCodeRepository extends JpaRepository<QRCode, Long>{

}
