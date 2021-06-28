package be.vdab.mail.service;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.mailing.LidMailing;
import be.vdab.mail.repository.LidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
@Service
@Transactional
public class DefaultLidService implements LidService {
    private final LidRepository lidRepository;
    private final LidMailing lidMailing;

    public DefaultLidService(LidRepository lidRepository, LidMailing lidMailing) {
        this.lidRepository = lidRepository;
        this.lidMailing = lidMailing;
    }

    @Override
    public void registreer(Lid lid, String ledenURL) {
        lidRepository.save(lid);
        lidMailing.stuurMailNaRegistratie(lid, ledenURL);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lid> findById(long id) {
        return lidRepository.findById(id);
    }
}
