package kniffelGUIAnforderungen;


// IF fuer die Anzeige und Steuerung der Wuerfel im GUI.

public interface WuerfelView {
    
	boolean[] getBehaltenStates();
    void zeigeWurf(int[] augen);
    void setBehaltenStates(boolean val);
    void clearBehaltenStates();
}
