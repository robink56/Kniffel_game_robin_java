package kniffelLogik;

import java.util.EnumMap;
import java.util.Map;

/**
 * Erzeugt einen Spieler
 * 
 * Haelt Name, eine EnumMap mit den erreichten 
 * Punkten zu jeder Kombi (einschliesslich Summenkategorien) und zaehlt
 * die erzielten Kniffel
 */
public class Spieler { 
    private final String name;
    private final Map<Kombi, Integer> punkte = new EnumMap<>(Kombi.class);
    private int kniffelZaehler = 0; 

    public Spieler(String name) { 
        this.name = name; 
    }

    public String getName() { 
        return name; 
    }

    // Liefert EnumMap mit den erzielten Punkten zu jeder Kombi
    public Map<Kombi, Integer> getPunkte() { 
        return punkte; 
    } 

    // Gibt zurueck, ob eine Kombi bereits belegt ist.
    public boolean hatSchon(Kombi kombi) { 
        return punkte.containsKey(kombi); 
    } 

    // Traegt die Punkte fuer eine Kombi in die EnumMap ein.
    // Erhoeht den Kniffelzaehler fuer Kniffel mit >= 50 Pkt.
    public void setPunkte(Kombi kombi, int wert) {
        punkte.put(kombi, wert);
        if (kombi == Kombi.KNIFFEL && wert >= 50) {
            kniffelZaehler++;
        }
    }

    // Liefert die Anzahl der erreichten Kniffel.
    public int getKniffelAnzahl() { 
        return kniffelZaehler; 
    }

    // Berechnet die Summe der oberen Kombis.
    public int getSummeOben() {
        return punkte.getOrDefault(Kombi.EINER, 0)
             + punkte.getOrDefault(Kombi.ZWEIER, 0)
             + punkte.getOrDefault(Kombi.DREIER, 0)
             + punkte.getOrDefault(Kombi.VIERER, 0)
             + punkte.getOrDefault(Kombi.FUENFER, 0)
             + punkte.getOrDefault(Kombi.SECHSER, 0);
    }

    // Berechnet den Bonus.
    public int getBonus() {
        return getSummeOben() >= 63 ? 35 : 0;
    }

    // Berechnet die Summe der oberen Kombis und des Bonus.
    public int getGesamtOben() {
        return getSummeOben() + getBonus();
    }

    // Berechnet die Summe der unteren Kombis.
    public int getSummeUnten() {
        return punkte.getOrDefault(Kombi.DREIER_PASCH, 0)
             + punkte.getOrDefault(Kombi.VIERER_PASCH, 0)
             + punkte.getOrDefault(Kombi.FULL_HOUSE, 0)
             + punkte.getOrDefault(Kombi.KLEINE_STRASSE, 0)
             + punkte.getOrDefault(Kombi.GROSSE_STRASSE, 0)
             + punkte.getOrDefault(Kombi.KNIFFEL, 0)
             + punkte.getOrDefault(Kombi.CHANCE, 0);
    }

    // Berechnet die Gesamtpunktzahl.
    public int getEndsumme() {
        return getGesamtOben() + getSummeUnten();
    }

    // Berechnet die Punktzahl einer Summenkategorie.
    // anhand der uebergebenen Kombi. Fuer echte Kombis wird 0 zurueckgegeben.
    public int berechneSonderwert(Kombi kombi) {
        switch (kombi) {
            case SUMME_OBEN:          return getSummeOben();
            case BONUS:               return getBonus();
            case GESAMT_OBEN:         return getGesamtOben();
            case SUMME_UNTEN:         return getSummeUnten();
            case SUMME_OBEN_NOCHMAL:  return getGesamtOben();
            case GESAMT_SUMME:        return getEndsumme();
            default:                  return 0;
        }
    }
}
