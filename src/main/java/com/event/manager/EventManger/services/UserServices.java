package com.event.manager.EventManger.services;

import com.event.manager.EventManger.payload.OtpDto;
import com.event.manager.EventManger.payload.RegisteredUserDTO;
import com.event.manager.EventManger.payload.UserResponse;

public interface UserServices {
    UserResponse addNewUser(RegisteredUserDTO userDTO);

    boolean verifyOtp(OtpDto otpDto);

    String sendOtp(String email);
}
