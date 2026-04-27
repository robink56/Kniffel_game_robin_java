package kniffelGUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Rechtes Panel (BorderLayout):
 * CENTER: Tabelle im ScrollPane
 * SOUTH:  DebugPanel
 *
 * Verwaltet Fonts, Spaltenbreite, Zeilenhoehe anpassen an Viewport
 * erste Spalte linksbuendig + Highlight,
 * Spieler-Spalten mit Farben.
 */
public class RechtesPanel extends JPanel {

    public RechtesPanel(JTable punkteTable, PunkteTabelle punkteTabelle, DebugPanel debugPanel) {
        super(new BorderLayout(8, 8));

        // Schriften
        punkteTable.setFont(punkteTable.getFont().deriveFont(14f));
        JTableHeader header = punkteTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));

        Dimension hd = header.getPreferredSize();
        hd.height = header.getFontMetrics(header.getFont()).getHeight() + 6;
        header.setPreferredSize(hd);

        // Erste Spalte breiter
        TableColumn c0 = punkteTable.getColumnModel().getColumn(0);
        c0.setMinWidth(160);
        c0.setPreferredWidth(230);

        // Erste Spalte linksbuendig und Highlight-Optik
        DefaultTableCellRenderer linksRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int zeile, int spalte) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, zeile, spalte);
                l.setHorizontalAlignment(SwingConstants.LEFT);

                boolean highlight = punkteTabelle.isHighlighted(zeile);

                if (isSelected) {
                    l.setOpaque(true);
                    Color selBg = table.getSelectionBackground();
                    l.setBackground(selBg);
                    l.setForeground(Farben.besteTextFarbe(selBg));
                    l.setFont(l.getFont().deriveFont(highlight ? Font.BOLD : Font.PLAIN));
                    return l;
                }

                if (highlight) {
                    l.setOpaque(true);
                    l.setBackground(new Color(230, 230, 230));
                    l.setForeground(Color.BLACK);
                    l.setFont(l.getFont().deriveFont(Font.BOLD));
                } else {
                    l.setOpaque(false);
                    l.setForeground(Color.BLACK);
                    l.setFont(l.getFont().deriveFont(Font.PLAIN));
                }
                return l;
            }
        };
        punkteTable.getColumnModel().getColumn(0).setCellRenderer(linksRenderer);

        // Spieler-Spalten einfaerben
        TableColumnModel cm = punkteTable.getColumnModel();
        for (int c = 1; c < cm.getColumnCount(); c++) {
            Color base = SpielFrame.spielerFarben[(c - 1) % SpielFrame.spielerFarben.length];
            Color bg   = Farben.farbTon(base, 0.08f);
            Color fg   = Farben.besteTextFarbe(bg);
            cm.getColumn(c).setCellRenderer(new Farben(bg, fg));
        }

        // Scrollpane + DebugPanel
        JScrollPane scroll = new JScrollPane(punkteTable);
        this.add(scroll, BorderLayout.CENTER);
        this.add(debugPanel, BorderLayout.SOUTH);

        // Zeilenhoehe anpassen an Viewport
        Runnable zeilenAnpassen = () -> {
            int zeilen = punkteTabelle.getRowCount();
            if (zeilen <= 0) return;
            int viewportH = scroll.getViewport().getExtentSize().height;
            int min = punkteTable.getFontMetrics(punkteTable.getFont()).getHeight() + 4;
            int zeilenH = Math.max(min, viewportH / zeilen);
            punkteTable.setRowHeight(zeilenH);
        };
        SwingUtilities.invokeLater(zeilenAnpassen);
        scroll.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { zeilenAnpassen.run(); }
        });
    }
}
