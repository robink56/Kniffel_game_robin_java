package kniffelLogik;

import java.util.Random;

/**
 * Berechnet einen Wurf mit 5 Zufallszahlen (5 Wuerfel)
 * Laesst Augenzahlen stehen, wenn fuer einen Wuerfel "Behalten" aktiviert ist.
 * Uebernimmt bei aktiviertem DebugModus die uebergebenen DebugAugenzahlen.
 */
public class WuerfelRechner { 
    private final int[] wurf = new int[5]; // Haelt aktuellen Wurf als Instanzvariable.

    public void wuerfelnAlle(boolean[] behaltenStates, boolean debugAktiv, int[] debugWerte) {
        Random r = new Random();
    	for (int i = 0; i < wurf.length; i++) {
            if (!behaltenStates[i]) {
                if (debugAktiv)  wurf[i] = debugWerte[i]; 
                else wurf[i] = 1 + r.nextInt(6);
            }
        }
    }
    
    // Setzt alle Wuerfelaugen auf 0 zurueck (kein gueltiger Wurf).
    public void reset() {
        for (int i = 0; i < wurf.length; i++) wurf[i] = 0;
    }

    // Gibt eien Kopie des aktuellen Wurfs zurueck.
    public int[] getWurf() {
        return wurf.clone();
    }
}
