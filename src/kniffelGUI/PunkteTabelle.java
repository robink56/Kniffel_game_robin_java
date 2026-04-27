package kniffelGUI;

import javax.swing.table.AbstractTableModel;

/**
 * Model-Adapter für die JTable der Punktetabelle.
 * 
 * Stellt die Spiellogik-Daten (Spieler, Kombis, Punkte)
 * der Swing-Oberfläche als TableModel zur Verfügung.
 */

import kniffelLogik.Kombi;
import kniffelLogik.Spieler;


import java.util.*;

public class PunkteTabelle extends AbstractTableModel { 

    private final List<Spieler> spielerListe = new ArrayList<>();

    // feste Zeilenindizes fuer Summen/Bonus
    private static final int ZEILE_SUMME_OBEN = 6;
    private static final int ZEILE_BONUS = 7;
    private static final int ZEILE_GESAMT_OBEN = 8;
    private static final int ZEILE_SUMME_UNTEN = 16;
    private static final int ZEILE_SUMME_OBEN_NOCHMAL = 17;
    private static final int ZEILE_GESAMTSUMME = 18;

    // Konstruktor: Spieler uebernehmen
    public PunkteTabelle(Spieler[] sL) {
        for (Spieler s : sL) spielerListe.add(s);
    }

    @Override 
    public int getRowCount() { return 19; } 

    @Override 
    public int getColumnCount() { return 1 + spielerListe.size(); }

    @Override 
    public String getColumnName(int column) {
        if (column == 0) return "Kombination";
        return spielerListe.get(column-1).getName();
    }

    // Highlight fuer waehlbare Zeilen
    private Set<Kombi> highlighted = EnumSet.noneOf(Kombi.class);

    // Setzt Kombi highlighted
    public void setHighlighted(Set<Kombi> kombis) {
        this.highlighted = EnumSet.copyOf(kombis);
        fireTableRowsUpdated(0, getRowCount() - 1);
    }

    public boolean isHighlighted(int zeile) {
        Kombi c = kombiFuerZeile(zeile);
        return c != null && highlighted.contains(c);
    }

    @Override 
    public Object getValueAt(int zeile, int spalte) {
        if (spalte == 0) return zeilenName(zeile);

        Spieler s = spielerListe.get(spalte - 1);
        Kombi kombi = kombiFuerZeile(zeile);

        if (kombi != null) {
            Integer w = s.getPunkte().get(kombi);
            return w == null ? "" : w;
        }

        // Summenwerte direkt aus Spieler
        switch (zeile) {
            case ZEILE_SUMME_OBEN:         return s.getSummeOben();
            case ZEILE_BONUS:              return s.getBonus();
            case ZEILE_GESAMT_OBEN:        return s.getGesamtOben();
            case ZEILE_SUMME_UNTEN:        return s.getSummeUnten();
            case ZEILE_SUMME_OBEN_NOCHMAL: return s.getGesamtOben();
            case ZEILE_GESAMTSUMME:        return s.getEndsumme();
        }
        return "";
    }

    // Zuordnung Zeile zur jeweiligen Kombi
    public Kombi kombiFuerZeile(int zeile) {
        switch (zeile) {
            case 0: return Kombi.EINER;
            case 1: return Kombi.ZWEIER;
            case 2: return Kombi.DREIER;
            case 3: return Kombi.VIERER;
            case 4: return Kombi.FUENFER;
            case 5: return Kombi.SECHSER;
            case 9: return Kombi.DREIER_PASCH;
            case 10: return Kombi.VIERER_PASCH;
            case 11: return Kombi.FULL_HOUSE;
            case 12: return Kombi.KLEINE_STRASSE;
            case 13: return Kombi.GROSSE_STRASSE;
            case 14: return Kombi.KNIFFEL;
            case 15: return Kombi.CHANCE;
            default: return null;
        }
    }

    // Zeilennamen (linke Spalte)
    private String zeilenName(int zeile) {  
        switch (zeile) {
            case 0: return "1er";
            case 1: return "2er";
            case 2: return "3er";
            case 3: return "4er";
            case 4: return "5er";
            case 5: return "6er";
            case ZEILE_SUMME_OBEN: return "gesamt";
            case ZEILE_BONUS: return "Bonus bei 63 oder mehr (plus 35)";
            case ZEILE_GESAMT_OBEN: return "gesamt oberer Teil";
            case 9: return "Dreierpasch";
            case 10: return "Viererpasch";
            case 11: return "Full House";
            case 12: return "Kleine Straße";
            case 13: return "Große Straße";
            case 14: return "Kniffel";
            case 15: return "Chance";
            case ZEILE_SUMME_UNTEN: return "gesamt unterer Teil";
            case ZEILE_SUMME_OBEN_NOCHMAL: return "gesamt oberer Teil";
            case ZEILE_GESAMTSUMME: return "Endsumme";
            default: return "";
        }
    }

    // prueft, ob eine Zeile eine Summenzeile ist
    public boolean isSumZeile(int z) {
        return z == ZEILE_SUMME_OBEN || z == ZEILE_BONUS || z == ZEILE_GESAMT_OBEN ||
               z == ZEILE_SUMME_UNTEN || z == ZEILE_SUMME_OBEN_NOCHMAL || z == ZEILE_GESAMTSUMME;
    }

    @Override 
    public boolean isCellEditable(int zeile, int spalte) { return false; }

    public void aktualisiereTabelle() { fireTableDataChanged(); }
}
