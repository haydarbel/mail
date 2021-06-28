package be.vdab.mail.mailing;

import be.vdab.mail.domain.Lid;

public interface LidMailing {
    void stuurMailNaRegistratie(Lid lid, String ledenURL);

    void stuurMailMetAantalLeden(long aantalLeden);

}
