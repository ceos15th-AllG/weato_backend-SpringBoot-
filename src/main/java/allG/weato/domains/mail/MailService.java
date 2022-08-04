package allG.weato.domains.mail;

import allG.weato.domains.mail.dto.SendingMailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "weato4u@gmail.com";

    public void mailSend(SendingMailDto sendingMailDto, int num){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(FROM_ADDRESS);
        mailMessage.setTo(sendingMailDto.getAddress());
        mailMessage.setSubject("WEATO 서비스의 뉴스레터 이메일 주소 인증 번호.");
        mailMessage.setText(Integer.toString(num));
        try{
            System.out.println("sending..");
            javaMailSender.send(mailMessage);
        }catch (MailException e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }
}
