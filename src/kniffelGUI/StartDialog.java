package kniffelGUI;

import javax.swing.*;
import kniffelLogik.Spieler;
import java.awt.*;
import java.util.ArrayList;

/**
 * StartDialog:
 * Ein kleines Fenster, in dem die Spieleranzahl gewaehlt
 * und die Namen der Spieler eingetragen werden.
 */

public class StartDialog extends JDialog {

    private final JTextField[] nameFelder = new JTextField[4];
    private final JComboBox<Integer> spielerzahlBox = new JComboBox<>(new Integer[]{2,3,4});
    private Spieler[] spielerListe;

    public StartDialog(JFrame elternFrame) {
        super(elternFrame, "Kniffel – Spieler anlegen", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // --- CENTER ---
        JPanel center = new JPanel(new GridLayout(6, 2, 8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        center.add(new JLabel("Anzahl Spieler (2–4):"));
        center.add(spielerzahlBox);

        // Namefelder
        for (int i = 0; i < 4; i++) {
            center.add(new JLabel("Name Spieler " + (i+1) + ":"));
            nameFelder[i] = new JTextField(  + (i+1) );
            center.add(nameFelder[i]);
        }

        // --- BUTTONS ---
        JButton startBtn = new JButton("Start");
        JButton cancelBtn = new JButton("Abbrechen");

        startBtn.addActionListener(e -> {
            int spielerZahl = (Integer) spielerzahlBox.getSelectedItem();
            ArrayList<Spieler> ps = new ArrayList<>();
            for (int i = 0; i < spielerZahl; i++) {
                String name = nameFelder[i].getText().trim();
                if (name.isEmpty()) name = "Spieler " + (i+1);
                ps.add(new Spieler(name));
            }
            spielerListe = ps.toArray(new Spieler[0]);
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            spielerListe = null; // kein Spiel gestartet
            dispose();
        });

        JPanel south = new JPanel();
        south.add(startBtn);
        south.add(cancelBtn);

        // --- FRAME ---
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        spielerzahlBox.addActionListener(e -> updateNameFields());

        pack();
        setMinimumSize(new Dimension(360, 260));
        setLocationRelativeTo(elternFrame);

        updateNameFields(); // Anfangszustand (2 Spieler sichtbar)
    }

    private void updateNameFields() {
        int spielerZahl = (Integer) spielerzahlBox.getSelectedItem();
        for (int i = 0; i < 4; i++) {
            nameFelder[i].setEnabled(i < spielerZahl); 
        }
    }

    public Spieler[] getSpielerListe() {
        return spielerListe;
    }
}