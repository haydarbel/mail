package be.vdab.mail.mailing;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.exceptions.KanMailNietZendenException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class DefaultLidMailing implements LidMailing {
    private final JavaMailSender sender;

    public DefaultLidMailing(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void stuurMailNaRegistratie(Lid lid, String ledenURL) {
        try {
            var message = sender.createMimeMessage();
            var helper = new MimeMessageHelper(message);
            helper.setTo(lid.getEmailAdres());
            helper.setSubject("Geregistreerd");
            var urlVanDeLidInfo = ledenURL + "/" + lid.getId();
            var tekst = "<h1>Je bent nu lid.</h1>Je nummer is:" + lid.getId() + "." +
                    "Je ziet je info <a href='" + urlVanDeLidInfo + "'>hier</a>.";
            helper.setText(tekst,true);
            sender.send(message);
        } catch (MailException | MessagingException e) {
            throw new KanMailNietZendenException(e);
        }
    }
}
