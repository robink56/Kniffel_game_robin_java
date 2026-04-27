package kniffelLogik;

import java.util.Arrays;


/**
 * Stellt statische Methoden zur Punkteberechnung bereit.
 *
 * Fuer jede eintragbare Kombination (echte Kombi) wird ueberprueft,
 * ob die Bedingungen erfuellt sind, und der entsprechende Punktwert
 * zurueckgegeben. Enthaelt ausserdem Hilfsmethoden wie Vielfachheiten
 * und Summen.
 */

public class PunkteRechner { 
	
    public static int punkteFuerKombi(int[] wurf, Kombi kombi) { 
        switch (kombi) {
            case EINER:   return wieOft(wurf, 1) * 1; 
            case ZWEIER:   return wieOft(wurf, 2) * 2;
            case DREIER: return wieOft(wurf, 3) * 3;
            case VIERER:  return wieOft(wurf, 4) * 4;
            case FUENFER:  return wieOft(wurf, 5) * 5;
            case SECHSER:  return wieOft(wurf, 6) * 6;
            case DREIER_PASCH: return is3erPasch(wurf) ? summeAugen(wurf) : 0;
            case VIERER_PASCH:  return is4erPasch(wurf)  ? summeAugen(wurf) : 0;
            case FULL_HOUSE:      return isFullHouse(wurf)   ? 25 : 0; 
            case KLEINE_STRASSE:  return isKleineStrasse(wurf) ? 30 : 0; 
            case GROSSE_STRASSE:  return isGrosseStrasse(wurf) ? 40 : 0;
            case KNIFFEL:         return isKniffel(wurf) ? 50 : 0;
            case CHANCE:          return summeAugen(wurf);
            default: return 0;
        }
    }
    
    // Erstellt zu einem Wurf ein Array mit den Vielfachheiten jeder 
    // moeglichen Augenzahl, wobei die Augenzahl dem Index des Elements entspricht.
    // Die 0 bleibt unbenutzt.
    public static int[] vielfH(int[] wurf) { 
        int[] welcheWieOft = new int[7]; 
        
        for (int i = 0; i < wurf.length; i++) {
            int augenzahl = wurf[i];
            welcheWieOft[augenzahl] = welcheWieOft[augenzahl] + 1;
        }
        return welcheWieOft;
    }
    
    // Gibt zurueck, wie oft eine bestimmte Augenzahl in einem Wurf vorkommt.
    public static int wieOft(int[] wurf, int augenzahl) { 
        return vielfH(wurf)[augenzahl];
    }

    public static boolean is3erPasch(int[] wurf) {  
        for (int anzahl : vielfH(wurf)) if (anzahl >= 3) return true;
        return false;
    }

    public static boolean is4erPasch(int[] wurf) {
        for (int anzahl : vielfH(wurf)) if (anzahl >= 4) return true;
        return false;
    }

    public static boolean isKniffel(int[] wurf) {
        for (int anzahl : vielfH(wurf)) if (anzahl == 5) return true;
        return false;
    }
    
    public static boolean isFullHouse(int[] wurf) {
	    int[] w = wurf.clone(); 
	    Arrays.sort(w);
	    return 	w[0]==w[1] && w[1]==w[2] && w[2]!=w[3 ]&& w[3]==w[4] ||
	    		w[0]==w[1] && w[1]!=w[2] && w[2]==w[3] && w[3]==w[4];
	}

    public static boolean isKleineStrasse(int[] w) { 
    	return 	vielfH(w)[1]>=1 && vielfH(w)[2]>=1 && vielfH(w)[3]>=1 && vielfH(w)[4]>=1 ||
    			vielfH(w)[2]>=1 && vielfH(w)[3]>=1 && vielfH(w)[4]>=1 && vielfH(w)[5]>=1 ||
    			vielfH(w)[3]>=1 && vielfH(w)[4]>=1 && vielfH(w)[5]>=1 && vielfH(w)[6]>=1;
    }

    public static boolean isGrosseStrasse(int[] w) { 
    	return 	vielfH(w)[2]>=1 && vielfH(w)[3]>=1 && vielfH(w)[4]>=1 && vielfH(w)[5]>=1 &&
    			(vielfH(w)[1]>=1 || vielfH(w)[6]>=1);
    }
    
    // Gibt die Summe aller Augen eines Wurfs zurueck.
    public static int summeAugen(int[] wurf) {
        int s = 0; for (int w : wurf) s += w; return s;
    }
    
}
