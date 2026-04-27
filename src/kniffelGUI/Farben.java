package kniffelGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Zentrale Klasse fuer Farb-Berechnungen und
 * Renderer fuer die SpielerSpalten in der Tabelle.
 */
public class Farben extends DefaultTableCellRenderer {

    private final Color baseBg;
    private final Color fg;

    public Farben(Color baseBg, Color fg) {
        this.baseBg = baseBg;
        this.fg = fg;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable tabelle, Object wert,
                                                   boolean isSelected, boolean imZellenfokus,
                                                   int zeile, int spalte) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(
                tabelle, wert, isSelected, imZellenfokus, zeile, spalte);

        if (!isSelected) {
            l.setOpaque(true);

            PunkteTabelle model = (PunkteTabelle) tabelle.getModel();
            boolean isSumme = model.isSumZeile(zeile);

            Color bg;
            if (isSumme) {
                bg = farbTon(baseBg, 0.00f); // dunkler für Summenzeilen
            } else {
                bg = farbTon(baseBg, 0.25f); // heller für normale Zeilen
            }

            l.setBackground(bg);
            l.setForeground(fg);
        }

        return l;
    }

    // Statische Hilfsmethoden
    
    /*
     * Hellt Farbton auf 
     * 0 <= faktor <= 1
     * faktor = 0 entspr. Originalfarbton
     * faktor = 1 entspr. weiss
     */ 

    public static Color farbTon(Color c, float faktor) {
        int r = (int) (c.getRed()   + (255 - c.getRed())   * faktor);
        int g = (int) (c.getGreen() + (255 - c.getGreen()) * faktor);
        int b = (int) (c.getBlue()  + (255 - c.getBlue())  * faktor);
        return new Color(r, g, b);
    }

    /*
     * Bestimmt optimale Schriftfarbe (schwarz/weiss) an Hand der Hintergrund-Helligkeit
     * 
     * Quelle der Formel: http://www.fseitz.de/blog/index.php?/archives/
     * 112-Helligkeit-von-Farben-des-RGB-Farbraums-berechnen.html
     */ 
    public static Color besteTextFarbe(Color bg) {
        int lumin = (int) Math.sqrt(
                0.299 * bg.getRed() * bg.getRed() +
                0.587 * bg.getGreen() * bg.getGreen() +
                0.114 * bg.getBlue() * bg.getBlue());
        return lumin >= 128 ? Color.BLACK : Color.WHITE;
    }
}
