package kniffelGUI;

import javax.swing.*;
import java.awt.*;

public class DebugPanel extends JPanel {
    private final JCheckBox aktivierCheckB;
    private final JSpinner[] stellRaeder = new JSpinner[5];
    private final JLabel hinweisLabel;
    private final JButton gewinnerBtn = new JButton("Zeige Gewinner vor Spielende");

    public DebugPanel() {
        setBorder(BorderFactory.createTitledBorder("Debug"));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Checkbox oben
        aktivierCheckB = new JCheckBox("Debug (Würfel manuell einstellen)");
        aktivierCheckB.setFont(aktivierCheckB.getFont().deriveFont(Font.PLAIN, 15f));
        aktivierCheckB.setOpaque(false);
        add(aktivierCheckB, gbc);

        aktivierCheckB.addActionListener(e -> aktualisiereBackground());

        // JSpinner Mitte
        JPanel spinnerGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        for (int i = 0; i < 5; i++) {
            stellRaeder[i] = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
            stellRaeder[i].setFont(stellRaeder[i].getFont().deriveFont(Font.PLAIN, 16f));
            JLabel lbl = new JLabel("W" + (i + 1) + ":");
            lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN, 15f));
            spinnerGroup.add(lbl);
            spinnerGroup.add(stellRaeder[i]);
        }
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(spinnerGroup, gbc);

        // Hinweis unten links, Gewinner-Vorschau-Button unten rechts
        JPanel untereZeile = new JPanel(new BorderLayout());
        untereZeile.setOpaque(false);

        hinweisLabel = new JLabel("Zum Übernehmen 'Würfeln' klicken!");
        hinweisLabel.setFont(hinweisLabel.getFont().deriveFont(Font.PLAIN, 15f));
        hinweisLabel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));

        untereZeile.add(hinweisLabel, BorderLayout.WEST);
        untereZeile.add(gewinnerBtn, BorderLayout.EAST);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(untereZeile, gbc);

        // Background
        aktualisiereBackground();
    }

    private void aktualisiereBackground() {
        if (aktivierCheckB.isSelected()) {
            setOpaque(true);
            setBackground(new Color(255, 200, 180));
        } else {
            setOpaque(false);
        }
        repaint();
    }

    public boolean isEnabledFlag() {
        return aktivierCheckB.isSelected();
    }

    public void setEnabledFlag(boolean v) {
        aktivierCheckB.setSelected(v);
    }

    public int[] getAugenZahlen() {
        int[] a = new int[5];
        for (int i = 0; i < 5; i++) a[i] = (Integer) stellRaeder[i].getValue();
        return a;
    }

    public JCheckBox getAktivierCheckB() {
        return aktivierCheckB;
    }

    public JButton getGewinnerButton() {
        return gewinnerBtn;
    }
}
