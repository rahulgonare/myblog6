package com.event.manager.EventManger.repository;

import com.event.manager.EventManger.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    OTP findByUserEmail(String email);
}
