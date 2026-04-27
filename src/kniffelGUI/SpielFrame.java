package kniffelGUI;

import kniffelGUIAnforderungen.SpielView;
import kniffelLogik.Kombi;
import kniffelLogik.Spieler;
import kniffelPresenter.SpielPresenter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.EnumSet;
import java.util.Set;
import java.util.List;

/**
 * Hauptfenster (BorderLayout)
 * WEST: links (LinkesPanel): kontrollPanel und WuerfelPanel
 * CENTER: rechts (RechtesPanel): Tabelle im ScrollPane und DebugPanel
 * 
 * Verwaltet die beiden Container links und rechts, in denen alle Komponenten liegen
 */
public class SpielFrame extends JFrame implements SpielView {

    private final Spieler[] spielerListe;
    private final SpielPresenter presenter;
    private int geklickteZeile = -1;

    // zwei Haupt-Panels
    private final LinkesPanel links;
    private final RechtesPanel rechts;
    
    
    // LinkesPanel
    private final JPanel kontrollPanel = new JPanel();  
    private final WuerfelPanel wuerfelPanel = new WuerfelPanel();
    
    // kontrollPanel (innerhalb von LinkesPanel)
    private final JLabel infoLabel   = new JLabel();
    private final JButton wuerfelBtn = new JButton("Würfeln");
    private final JButton waehlenBtn = new JButton("Kombination wählen");
    private final JButton nullBtn    = new JButton("0 eintragen");

    
    // RechtesPanel
    private final PunkteTabelle punkteTabelle; // Model-Adapter PunkteTabelle
    private final JTable punkteTable; // Swing-Komponente fuer Tabelle
    private final DebugPanel debugPanel = new DebugPanel();
    
    
    // Spielerfarben
    public static final Color[] spielerFarben = {
        new Color(30,10,140),
        new Color(60,100,50),
        new Color(200,130,30),
        new Color(170,0,0)
    };

    public SpielFrame(Spieler[] spielerListe) {
        super("Kniffel – Swing");
        this.spielerListe = spielerListe;
        this.punkteTabelle = new PunkteTabelle(spielerListe);
        this.punkteTable = new JTable(punkteTabelle);
        this.presenter = new SpielPresenter(spielerListe, this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setSize(1100, 650);

        // Panels erzeugen
        this.links  = new LinkesPanel(kontrollPanel, infoLabel, wuerfelBtn, waehlenBtn, nullBtn, wuerfelPanel);
        this.rechts = new RechtesPanel(punkteTable, punkteTabelle, debugPanel);

        punkteTable.setSelectionBackground(new Color(70,70,60));
        add(links,  BorderLayout.WEST);
        add(rechts, BorderLayout.CENTER);

        aktionenVerknuepfen();
        presenter.naechster();
        setButtonStates(true, false);
        aktualisiereInfo();
    }

    // ActionEvents
    private void aktionenVerknuepfen() {
        // Tabelle: genau eine Zeile wählbar
        punkteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        punkteTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) ->
            geklickteZeile = punkteTable.getSelectedRow()
        );

        wuerfelBtn.addActionListener(e -> {
            if (debugPanel.isEnabledFlag()) {
                presenter.setDebugAugen(debugPanel.getAugenZahlen());
            }
            presenter.wuerfeln();
            setButtonStates(true, true);
            aktualisiereInfo();
        });

        waehlenBtn.addActionListener(e -> {
            if (ungueltigeEingabe()) return;
            Kombi k = punkteTabelle.kombiFuerZeile(geklickteZeile);
            if (presenter.punkteEintragen(k)) endOrNext();
            aktualisiereInfo();
        });

        nullBtn.addActionListener(e -> {
            if (ungueltigeEingabe()) return;
            Kombi kombi = punkteTabelle.kombiFuerZeile(geklickteZeile);
            if (presenter.nullEintragen(kombi)) endOrNext();
            aktualisiereInfo();
        });

        debugPanel.getAktivierCheckB().addActionListener(e ->
            presenter.setDebugAktiv(debugPanel.isEnabledFlag())
        );

        debugPanel.getGewinnerButton().addActionListener(e -> {
            List<Spieler> gewinner = presenter.ermittleGewinner();
            zeigeGewinner(gewinner, presenter.getSpielerListe());
        });
    }

    // Setzt Einstellungen fuer naechsten Spieler, wenn Spiel noch nicht zu Ende
    // Fuehrt zeigeGewinner aus, wenn Spiel zu Ende
    private void endOrNext() {
        if (presenter.isGameOver()) {
            List<Spieler> gewinner = presenter.ermittleGewinner();
            zeigeGewinner(gewinner, presenter.getSpielerListe());
            setButtonStates(false, false);
            return;
        }
        presenter.naechster();
        geklickteZeile = -1;
        punkteTable.clearSelection();
        setButtonStates(true, false);
    }

    // Aktiviert oder deaktiviert WuerfelButton und beide WaehlenButtons 
    private void setButtonStates(boolean kannWuerfeln, boolean kannAuswaehlen) {
        wuerfelBtn.setEnabled(kannWuerfeln);
        waehlenBtn.setEnabled(kannAuswaehlen);
        nullBtn.setEnabled(kannAuswaehlen);
    }

    private boolean ungueltigeEingabe() {
        if (geklickteZeile < 0 || punkteTabelle.isSumZeile(geklickteZeile)) {
            JOptionPane.showMessageDialog(this, "Bitte eine Zeile mit Würfel-Kombination wählen.");
            return true;
        }
        return false;
    }

    private void aktualisiereInfo() {
        infoLabel.setText("Am Zug: " + presenter.getAktuellerSpieler().getName()
                + " | Würfe übrig: " + presenter.getWuerfeUebrig());
        aktuelleSpielerfarbeEinstellen();
    }

    // setzt Spielerfarbe fuer InfoPanel und WuerfelPanel 
    private void aktuelleSpielerfarbeEinstellen() {
        int i = aktuelleSpielerNummer();
        Color base = spielerFarben[i % spielerFarben.length];

        infoLabel.setOpaque(true);
        Color infoBg = Farben.farbTon(base, 0.30f);
        infoLabel.setBackground(infoBg);
        infoLabel.setForeground(Farben.besteTextFarbe(infoBg));

        // LinkesPanel einfaerben
        links.setOpaque(true);
        links.setBackground(Farben.farbTon(base, 0.12f));

        // WuerfelPanel einfaerben
        wuerfelPanel.setOpaque(true);
        wuerfelPanel.setBackground(Farben.farbTon(base, 0.18f));

        wuerfelBtn.setEnabled(presenter.getWuerfeUebrig() != 0);

        links.repaint();
    }


    private int aktuelleSpielerNummer() {
        for (int i = 0; i < spielerListe.length; i++) {
            if (spielerListe[i] == presenter.getAktuellerSpieler()) return i;
        }
        return 0;
    }

    // ---- SpielView ----
    @Override public void aktualisiereTabelle() {
        punkteTabelle.fireTableDataChanged();
    }

    @Override public void zeigeWurf(int[] augen) {
        wuerfelPanel.zeigeWurf(augen);
    }

    @Override public boolean[] getBehaltenStates() {
        return wuerfelPanel.getBehaltenStates();
    }

    @Override public void clearBehaltenStates() {
        wuerfelPanel.clearBehaltenStates();
    }

    @Override public void clearWaehlbare() {
        punkteTabelle.setHighlighted(EnumSet.noneOf(Kombi.class));
        punkteTabelle.fireTableDataChanged();
    }
    
    // Setzt Kombi waehlbar in Tabelle
    @Override public void setWaehlbar(Set<Kombi> set) {
        punkteTabelle.setHighlighted(set);
        punkteTabelle.fireTableDataChanged();
    }

    // Uebergebenen String als Dialog zeigen
    @Override
    public void zeigeHinweis(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Dialog: 0 Punkte eintragen?
    @Override
    public boolean bestaetigeNullEintrag() {
        int opt = JOptionPane.showConfirmDialog(
            this,
            "Diese Kategorie passt nicht. 0 Punkte eintragen?",
            "0 Punkte?",
            JOptionPane.YES_NO_OPTION
        );
        return opt == JOptionPane.YES_OPTION;
    }

    // Zeigt GewinnerDialog mit Spielergebnis
    @Override
    public void zeigeGewinner(List<Spieler> gewinnerListe, Spieler[] alle) {
        String text = "Spiel beendet!\n\n";

        for (Spieler s : alle) {
            text += s.getName() + ": " + s.getEndsumme() + " Punkte\n";
        }

        text += "\nGewinner: ";
        if (gewinnerListe.size() == 1) {
            text += gewinnerListe.get(0).getName();
        } else {
            for (int i = 0; i < gewinnerListe.size(); i++) {
                text += gewinnerListe.get(i).getName();
                if (i < gewinnerListe.size() - 1) {
                    text += ", ";
                }
            }
        }

        JOptionPane.showMessageDialog(this, text);
    }

}
