package kniffelPresenter;

import kniffelGUIAnforderungen.SpielView;
import kniffelLogik.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Spiel-Presenter
 * Verknuepft Logik mit View
 * Verarbeitet ActionEvents und treibt Ablauf voran
 */


public class SpielPresenter {
    private final Spieler[] spielerListe;
    private final SpielView view;
    private final WuerfelRechner wurf = new WuerfelRechner(); 

    private int aktuellerSpieler = -1; 
    private int wuerfelUebrig = 3;  
    private boolean debugAktiv;
    private int[] debugAugen = new int[5];  
    private boolean hatSchonGewuerfelt = false; 

    public SpielPresenter(Spieler[] spielerListe, SpielView view) {
        this.spielerListe = spielerListe;
        this.view = view;
    }

    public Spieler getAktuellerSpieler() { return spielerListe[aktuellerSpieler]; }
    public Spieler[] getSpielerListe() { return spielerListe; }
    public int getWuerfeUebrig() { return wuerfelUebrig; }

    // Setzt Einstellungen, wenn naechster Spieler dran ist
    // Wechselt zum naechsten Spieler, setzt Wuerfe zurueck, leert Auswahl und zeigt leeren Wurf
    public void naechster() {
        aktuellerSpieler = (aktuellerSpieler + 1) % spielerListe.length;
        wuerfelUebrig = 3;
        hatSchonGewuerfelt = false;
        wurf.reset();
        view.clearBehaltenStates(); 
        view.clearWaehlbare(); 
        view.zeigeWurf(wurf.getWurf());  
    }

    public void wuerfeln() {
        boolean[] behaltenStates = view.getBehaltenStates();
        if (debugAktiv) {
            wurf.wuerfelnAlle(behaltenStates, true, debugAugen);
        } else {
            wurf.wuerfelnAlle(behaltenStates, false, new int[5]);
        }
        view.zeigeWurf(wurf.getWurf());
        wuerfelUebrig--;
        hatSchonGewuerfelt = true;
        aktualisiereWaehlbare();
        view.aktualisiereTabelle(); //NEU wegen Bug
    }

    // Prueft, ob Kombi schon belegt, Berechnet Punkte fuer Kombi,
    // prueft zusaetzlichen Kniffel, fragt 0-Eintrag ab
    public boolean punkteEintragen(Kombi kombi) {
        Spieler s = getAktuellerSpieler();
        if (s.hatSchon(kombi)) {
            view.zeigeHinweis("Kombination bereits belegt.");
            return false;
        }

        int[] augenZahlen = wurf.getWurf();
        int punkte = PunkteRechner.punkteFuerKombi(augenZahlen, kombi);

        boolean isKniffel = PunkteRechner.isKniffel(augenZahlen);
        boolean hatSchonKniffel = s.getPunkte().containsKey(Kombi.KNIFFEL)
                                 && s.getPunkte().get(Kombi.KNIFFEL) >= 50;

        if (isKniffel && hatSchonKniffel && kombi != Kombi.KNIFFEL) {
            punkte = 100;
        }

        if (punkte == 0 && kombi != Kombi.CHANCE) {
            if (!view.bestaetigeNullEintrag()) return false;
        }

        s.setPunkte(kombi, punkte);
        view.aktualisiereTabelle();
        return true;
    }

    public boolean nullEintragen(Kombi kombi) {
        Spieler p = getAktuellerSpieler();
        if (p.hatSchon(kombi)) {
            view.zeigeHinweis("Kategorie bereits belegt.");
            return false;
        }
        p.setPunkte(kombi, 0);
        view.aktualisiereTabelle();
        return true;
    }

    // Prueft, ob alle Kombis aller Spieler belegt sind
    public boolean isGameOver() { 
        for (Spieler s : spielerListe) {
            for (Kombi k : Kombi.values()) {
                if (k.isEintragbar() && !s.getPunkte().containsKey(k)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Gibt Liste mit Gewinner(n) zurueck
    public List<Spieler> ermittleGewinner() { 
        int maxPunkte = 0;
        for (Spieler s : spielerListe) {
            if (s.getEndsumme() > maxPunkte) {
                maxPunkte = s.getEndsumme();
            }
        }

        List<Spieler> gewinnerListe = new ArrayList<>();
        for (Spieler s : spielerListe) {
            if (s.getEndsumme() == maxPunkte) {
                gewinnerListe.add(s);
            }
        }
        return gewinnerListe;
    }

    public void setDebugAktiv(boolean aktiv) { this.debugAktiv = aktiv; }
    
    // Uebergibt manuell eingestellten Wurf
    public void setDebugAugen(int[] vals)  { this.debugAugen = vals.clone(); }

    private void aktualisiereWaehlbare() {
        if (!hatSchonGewuerfelt) {
            view.clearWaehlbare();
            return;
        }

        Spieler s = getAktuellerSpieler();
        int[] augenZahlen = wurf.getWurf();

        boolean isKniffel = PunkteRechner.isKniffel(augenZahlen);
        boolean hatSchonKniffel = s.getPunkte().containsKey(Kombi.KNIFFEL)
                                 && s.getPunkte().get(Kombi.KNIFFEL) >= 50;

        EnumSet<Kombi> waehlbar = EnumSet.noneOf(Kombi.class);
        for (Kombi k : Kombi.values()) {
            if (!k.isEintragbar()) continue;  // Summenzeilen ueberspringen
            if (s.hatSchon(k)) continue;

            int pkte = PunkteRechner.punkteFuerKombi(augenZahlen, k);
            if (pkte > 0) waehlbar.add(k);
            if (isKniffel && hatSchonKniffel && k != Kombi.KNIFFEL) waehlbar.add(k);
        }
        view.setWaehlbar(waehlbar);
    }
}
