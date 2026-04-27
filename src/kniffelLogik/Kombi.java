package kniffelLogik;

/**
 * Aufzaehlung der wertbaren Augen-Kombinationen und der Summenkategorien
 * 
 * Liefert Zeilennamen und eine Flag, ob echte Kombi (eintragbar) oder Summenzeile
 */
public enum Kombi {
    EINER("1er", true),
    ZWEIER("2er", true),
    DREIER("3er", true),
    VIERER("4er", true),
    FUENFER("5er", true),
    SECHSER("6er", true),

    SUMME_OBEN("Gesamt oben", false),
    BONUS("Bonus bei 63 oder mehr (35)", false),
    GESAMT_OBEN("Gesamt oberer Teil", false),

    DREIER_PASCH("Dreierpasch", true),
    VIERER_PASCH("Viererpasch", true),
    FULL_HOUSE("Full House", true),
    KLEINE_STRASSE("Kleine Straße", true),
    GROSSE_STRASSE("Große Straße", true),
    KNIFFEL("Kniffel", true),
    CHANCE("Chance", true),

    SUMME_UNTEN("Gesamt unterer Teil", false),
    SUMME_OBEN_NOCHMAL("Gesamt oberer Teil", false),
    GESAMT_SUMME("Endsumme", false);

    
    private final String anzeigename;
    private final boolean eintragbar; 

    Kombi(String anzeigename, boolean eintragbar) {
        this.anzeigename = anzeigename;
        this.eintragbar = eintragbar;
    }

    public String getAnzeigename() {
        return anzeigename;
    }

    public boolean isEintragbar() {
        return eintragbar;
    }
}
