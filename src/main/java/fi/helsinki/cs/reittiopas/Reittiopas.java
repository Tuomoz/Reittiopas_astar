package fi.helsinki.cs.reittiopas;

import fi.helsinki.cs.reittiopas.logiikka.Pysakki;
import fi.helsinki.cs.reittiopas.logiikka.Tila;

import java.util.HashSet;
import java.util.PriorityQueue;

public class Reittiopas {

    private Vertailija vertailija;

    /**
     * Toteuta A*-haku. Palauta reitti taaksepäin linkitettynä listana
     * Tila-olioita, joista ensimmäinen osoittaa maalipysäkkiin ja jokainen
     * tuntee pysäkin ja tilan, josta kyseiseen tilaan päästiin (viimeisen
     * solmun Pysäkki on lähtöpysäkki ja edellinen tila on null). Pidä myös
     * reitin ajallisesta kestosta kirjaa Tila-olioissa.
     *
     * Voit selvittää pysäkin naapuripysäkit, eli pysäkit joihin pysäkiltä on
     * suora yhteys, kutsumalla pysäkin getNaapurit() -metodia.
     *
     * Voit selvittää pysäkiltä nopeimman siirtymän keston jollekin sen
     * naapuripysäkeistä kutsumalla pysäkin nopeinSiirtyma(Pysakki p, int aika)
     * -metodia, joka ottaa parametrina naapuripysäkin ja tähän saakka kuljetun
     * reitin keston.
     *
     * Alussa luodaan Vertailija-olio (jonka toiminnallisuuden olet toteuttanut)
     * ja annetaan se konstruktorin parametrina PriorityQueue:lle. Nyt
     * PriorityQueue käyttää kirjoittamaasi vertailijaa ja palauttaa tilat
     * A*-haun kannalta järkevässä järjestyksessä.
     *
     * @param lahto Lahtopysakin koodi
     * @param maali Maalipysakin koodi
     * @param aikaAlussa Aika lähtöhetkellä
     * @return Tila-olioista koostuva linkitetty lista maalista lähtötilaan
     */
    public Tila haku(Pysakki lahto, Pysakki maali, int aikaAlussa) {
        this.vertailija = new Vertailija(maali);
        PriorityQueue<Tila> tutkittavat = new PriorityQueue<>(vertailija);
        HashSet<Pysakki> kasitellyt = new HashSet<>();
        Tila alkutila = new Tila(lahto, null, aikaAlussa); //(pysäkki, edellinen tila, kulunut aika)

        while (!tutkittavat.isEmpty()) {
            Tila current_tila = tutkittavat.remove();
            Pysakki current_pysakki = current_tila.getPysakki();
            if (current_pysakki.equals(maali))
                return current_tila;
            kasitellyt.add(current_pysakki);
            for (Pysakki naapuri : current_pysakki.getNaapurit()) {
                if (kasitellyt.contains(naapuri))
                    continue;
                Tila uusi_tila = new Tila(naapuri, current_tila, current_pysakki.nopeinSiirtyma(naapuri, current_tila.getNykyinenAika()));

                tutkittavat.add(uusi_tila);
            }
        }
        return null;
    }
}
