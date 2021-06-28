package be.vdab.mail.mailing;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.exceptions.KanMailNietZendenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Component
public class DefaultLidMailing implements LidMailing {
    private final JavaMailSender sender;
    private final String emailAdresWebmaster;

    public DefaultLidMailing(JavaMailSender sender,@Value("${emailAdresWebMaster}") String emailAdresWebmaster) {
        this.sender = sender;
        this.emailAdresWebmaster = emailAdresWebmaster;
    }

    @Override
    @Async
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

    @Override
    public void stuurMailMetAantalLeden(long aantalLeden) {
        try {
            var message = new SimpleMailMessage();
            message.setTo(emailAdresWebmaster);
            message.setSubject("Aantal Leden : ");
            message.setText(aantalLeden + "leden");
            sender.send(message);
        } catch (MailException e) {
            throw new KanMailNietZendenException(e);
        }
    }

}
