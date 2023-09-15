package com.event.manager.EventManger.services.serviceImpl;

import com.event.manager.EventManger.entity.Event;
import com.event.manager.EventManger.entity.OTP;
import com.event.manager.EventManger.entity.RegisteredUser;
import com.event.manager.EventManger.entity.Temporary;
import com.event.manager.EventManger.payload.OtpDto;
import com.event.manager.EventManger.payload.RegisteredUserDTO;
import com.event.manager.EventManger.payload.UserResponse;
import com.event.manager.EventManger.repository.EventRepository;
import com.event.manager.EventManger.repository.OTPRepository;
import com.event.manager.EventManger.repository.RegisteredUserRepository;
import com.event.manager.EventManger.repository.TemporaryRepository;
import com.event.manager.EventManger.services.UserServices;
import com.event.manager.EventManger.utility.SendOTPMail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImple implements UserServices {

    private final TemporaryRepository temporaryRepository;
    private final RegisteredUserRepository registeredUserRepository;
    private final OTPRepository otpRepository;
    private final SendOTPMail sendOTPMail;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;


    @Autowired
    public UserServiceImple(TemporaryRepository temporaryRepository, RegisteredUserRepository registeredUserRepository, OTPRepository otpRepository, SendOTPMail sendOTPMail, ModelMapper modelMapper, EventRepository eventRepository) {
        this.temporaryRepository = temporaryRepository;
        this.registeredUserRepository = registeredUserRepository;
        this.otpRepository = otpRepository;
        this.sendOTPMail = sendOTPMail;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public UserResponse addNewUser(RegisteredUserDTO userDTO) {

        Temporary temporary = new Temporary();
        temporary.setName(userDTO.getName());
        temporary.setCity(userDTO.getCity());
        temporary.setEmail(userDTO.getEmail());
        temporary.setPhone(userDTO.getPhone());
        temporary.setSchoolName(userDTO.getSchoolName());
        temporary.setState(userDTO.getState());
        temporary.setVerified(false);

        temporary = temporaryRepository.save(temporary);

        sendOtp(temporary.getEmail());

        return mapToResponse(temporary);
    }

    @Override
    public boolean verifyOtp(OtpDto otpDto) {

        Temporary unverifiedUser = temporaryRepository.findByEmail(otpDto.getEmail());
        RegisteredUser verifiedUser = registeredUserRepository.findByEmail(otpDto.getEmail());

        OTP otp = otpRepository.findByUserEmail(otpDto.getEmail());



        if(otp!= null && LocalDateTime.now().isBefore(otp.getExpirationTime())){

            if(otp.getOtp().equals(otpDto.getOtp()))
            {

                if(unverifiedUser!=null){

                    unverifiedUser.setVerified(true);
                    RegisteredUser registeredUser = new RegisteredUser();
                    modelMapper.map(unverifiedUser, registeredUser);
                    RegisteredUser registered = registeredUserRepository.save(registeredUser);
                    temporaryRepository.delete(unverifiedUser);
                    Event event = new Event();
                    event.setRegisteredUser(registered);
                    event.setLocalTime(LocalDateTime.now());
                    eventRepository.save(event);
                    return true;

                }
                else if(verifiedUser!=null){

                    Event event = new Event();
                    event.setRegisteredUser(verifiedUser);
                    event.setLocalTime(LocalDateTime.now());
                    eventRepository.save(event);
                    return true;

                }


            }
            else
                return false;

        }


        return false;
    }

    @Override
    public String sendOtp(String email) {
        try {
            OTP otp = otpRepository.findByUserEmail(email);

            if(otp == null) {
                otp = new OTP();
                otp.setUserEmail(email);

            }
            otp.setOtp(generateOTP());
            otp.setExpirationTime(LocalDateTime.now().plusMinutes(10));

            sendOTPMail.sendOTPByEmail(email, otp.getOtp());

            otpRepository.save(otp);
            return "Otp Sent Successfully, Please verify it in verify API END POINTS";
        }catch (Exception e){
            return "Error Please try again";
        }
    } //purani wali ko override

    private UserResponse mapToResponse(Temporary registeredUser) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(registeredUser.getId());
        userResponse.setName(registeredUser.getName());
        userResponse.setEmail(registeredUser.getEmail());
        userResponse.setMessage("Successfully registered now Verify otp now for next step");

        return userResponse;
    }

    private String generateOTP() {

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return Integer.toString(otp);
    }

}
