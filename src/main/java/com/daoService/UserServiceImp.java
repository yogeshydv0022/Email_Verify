package com.daoService;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.Utils.EmailService;
import com.dto.request.RegisterRequest;
import com.dto.response.ResponseRequest;
import com.model.User;
import com.repository.UserRepository;
import com.repository.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
	
	private final UserRepository userRepository;
	private final EmailService emailService;

    @Override
    public ResponseRequest registor(RegisterRequest registerRequest) {
       User existingUser = userRepository.findByEmail(registerRequest.getEmail());
       if (existingUser != null && existingUser.isVerified()){
           throw new RuntimeException("User Already Registered");
       }
        User users = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
        String otp = generateOTP();
        users.setOtp(otp);

        User savedUser = userRepository.save(users);
        sendVerificationEmail(savedUser.getEmail(), otp);

        ResponseRequest response = ResponseRequest.builder()
                .name(users.getName())
                .email(users.getEmail())
                .build();
        return response;
    }

    @Override
    public void verify(String email, String otp) {
        User users = userRepository.findByEmail(email);
        if (users == null){
            throw new RuntimeException("User not found");
        } else if (users.isVerified()) {
            throw new RuntimeException("User is already verified");
        } else if (otp.equals(users.getOtp())) {
            users.setVerified(true);
            userRepository.save(users);
        }else {
            throw new RuntimeException("Internal Server error");
        }
    }


    private String generateOTP(){
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email,String otp){
        String subject = "Email verification";
        String body ="your verification otp is: "+otp;
        emailService.sendEmail(email,subject,body);
    }

	@Override
	public User Login(String email, String password) {
		User byemail=userRepository.findByEmail(email);
		if(byemail!=null && byemail.isVerified() && byemail.getPassword().equals(password)) {
			return byemail;
		}else {
			throw new RuntimeException("internal server error");
		}
	}


	
	

}
