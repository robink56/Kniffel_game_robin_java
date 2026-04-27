package kniffelGUI;

import javax.swing.*;
import java.awt.*;


/**
 * Linkes Panel (BorderLayout):
 * NORTH: kontrollPanel (InfoLabel + Buttons)
 * CENTER: WuerfelPanel
 * 
 * Verwaltet Settings fuer kontrollPanel und WuerfelPanel,
 * die sich im Verlauf des Spieles nicht mehr aendern.
 */
public class LinkesPanel extends JPanel {

	public LinkesPanel(JPanel kontrollPanel,
            JLabel infoLabel,
            JButton wuerfelBtn,
            JButton waehlenBtn,
            JButton nullBtn,
            WuerfelPanel wuerfelPanel) {

        super(new BorderLayout());

        // Settings fuer Container mit InfoLabel + Buttons
        kontrollPanel.setLayout(new BoxLayout(kontrollPanel, BoxLayout.Y_AXIS));
        kontrollPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        kontrollPanel.setOpaque(false);

        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 24f));

        wuerfelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        wuerfelBtn.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 24f));

        waehlenBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        waehlenBtn.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 24f));

        nullBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nullBtn.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 24f));

        kontrollPanel.add(infoLabel);
        kontrollPanel.add(Box.createVerticalStrut(30));
        kontrollPanel.add(wuerfelBtn);
        kontrollPanel.add(Box.createVerticalStrut(30));
        kontrollPanel.add(waehlenBtn);
        kontrollPanel.add(Box.createVerticalStrut(30));
        kontrollPanel.add(nullBtn);

        // Internes kontrolPanel und externes wuerfelPanel zu LinkesPanel hinzufuegen
        this.add(kontrollPanel, BorderLayout.NORTH);
        this.add(wuerfelPanel, BorderLayout.CENTER);
    }
}
