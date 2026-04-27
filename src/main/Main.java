package main;

import kniffelGUI.SpielFrame;
import kniffelGUI.StartDialog;
import kniffelLogik.Spieler;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            StartDialog start = new StartDialog(null);
            start.setVisible(true);
            Spieler[] spielerListe = start.getSpielerListe();
            if (spielerListe != null && spielerListe.length >= 2) {
                SpielFrame spielFrm = new SpielFrame(spielerListe);
                spielFrm.setLocationRelativeTo(null);
                spielFrm.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
