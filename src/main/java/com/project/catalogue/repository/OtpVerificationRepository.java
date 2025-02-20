package com.project.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catalogue.model.OTP_Verification;

public interface OtpVerificationRepository extends JpaRepository<OTP_Verification, Long> {
    
}


