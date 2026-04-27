package kniffelGUI;

import javax.swing.*;
import kniffelGUIAnforderungen.WuerfelView;
import java.awt.*;

/**
 * WuerfelPanel:
 * Zeigt die fuenf Wuerfel grafisch an und erlaubt,
 * Wuerfel mit "Behalten"-Checkboxen festzuhalten.
 */


public class WuerfelPanel extends JPanel implements WuerfelView { 
    private final JLabel[] wuerfelLabels = new JLabel[5];
    private final JCheckBox[] behaltenCheckBoxes = new JCheckBox[5]; 

    public WuerfelPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);

        Font wuerfelGroesse = new Font(Font.SANS_SERIF, Font.BOLD, 82); 

        for (int i = 0; i < 5; i++) {
            JPanel box = new JPanel(new BorderLayout());
            box.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel lbl = new JLabel(wuerfelSymbolFuer(0), SwingConstants.CENTER);
            lbl.setFont(wuerfelGroesse);
            wuerfelLabels[i] = lbl;
            box.add(lbl, BorderLayout.CENTER);

            behaltenCheckBoxes[i] = new JCheckBox("Behalten");
            box.add(behaltenCheckBoxes[i], BorderLayout.SOUTH);

            gbc.gridx = i; gbc.gridy = 0;
            add(box, gbc);
        }
    }

    @Override
    public boolean[] getBehaltenStates() { 
        boolean[] h = new boolean[5];
        for (int i = 0; i < 5; i++) h[i] = behaltenCheckBoxes[i].isSelected();
        return h;
    }

    @Override
    public void zeigeWurf(int[] augen) { 
        for (int i = 0; i < 5; i++) wuerfelLabels[i].setText(wuerfelSymbolFuer(augen[i]));
    }

    
    @Override
    public void setBehaltenStates(boolean val) { 
        for (JCheckBox b : behaltenCheckBoxes) b.setSelected(val);
    }
	
    
    @Override
    public void clearBehaltenStates() { 
        setBehaltenStates(false);
    }
	
    
    private String wuerfelSymbolFuer(int augenZ) {
        final String[] symbole = {"?","⚀","⚁","⚂","⚃","⚄","⚅"};
        return symbole[augenZ];
    }
}

  
