package allG.weato.domains.mail;

import allG.weato.domains.mail.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.AssociationOverride;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "weato4u@gmail.com";

    public void mailSend(MailDto mailDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        javaMailSender.send(mailMessage);
    }
}
