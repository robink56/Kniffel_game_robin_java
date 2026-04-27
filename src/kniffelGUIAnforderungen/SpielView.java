package kniffelGUIAnforderungen;

import java.util.List;
import java.util.Set;
import kniffelLogik.Kombi;
import kniffelLogik.Spieler;

//IF fuer die Spielfenster Ansicht (GUI). 

public interface SpielView {  
    void aktualisiereTabelle(); 
    void setWaehlbar(Set<Kombi> set); 
    void clearBehaltenStates(); 
    void clearWaehlbare();
    void zeigeWurf(int[] augen); 
    boolean[] getBehaltenStates();
    void zeigeHinweis(String msg);
    boolean bestaetigeNullEintrag();  
    void zeigeGewinner(List<Spieler> gewinner, Spieler[] alle);
}
