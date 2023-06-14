package tn.pfe.spring.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.pfe.spring.Service.EmailService;

//import tn.pfe.spring.entities.Reservation;

@Service
//Class
//Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

 @Autowired private JavaMailSender javaMailSender;

 @Value("${spring.mail.username}") private String sender;

 // Method 1
 // To send a simple email
 public String sendSimpleMail(String Toemail,String Text,String Subject)
 {

     // Try block to check for exceptions
     try {

         // Creating a simple mail message
         SimpleMailMessage mailMessage
             = new SimpleMailMessage();

         // Setting up necessary details
         mailMessage.setFrom("aya258ghoul@gmail.com");
         mailMessage.setTo(Toemail);
         mailMessage.setText(Text);
         mailMessage.setSubject("cofirmation de réservation");

         // Sending the mail
         javaMailSender.send(mailMessage);
        return "Add reservation successfully...";
     }

     // Catch block to handle the exceptions
     catch (Exception e) {
    	 return "Error while Sending Mail";
     }
 }





}
