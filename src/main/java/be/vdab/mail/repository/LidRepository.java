package be.vdab.mail.repository;

import be.vdab.mail.domain.Lid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidRepository extends JpaRepository<Lid, Long> {

}
