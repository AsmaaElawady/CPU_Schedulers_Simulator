import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.*;

public class ChartGUI {
    ArrayList<Process> Processes = new ArrayList<Process>();
    static JFrame frame = new JFrame("CPU Scheduler Chart");
    static GridBagConstraints gbc = new GridBagConstraints();
    static JPanel panal = new JPanel();

    public ChartGUI(ArrayList<Process> P) {
        this.Processes = P;
        panal.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5);

        JLabel ProcessName;
        for (int i = 0; i < Processes.size(); i++) {
            ProcessName = new JLabel(Processes.get(i).getName());
            gbc.gridx = 0;
            gbc.gridy = i;
            panal.add(ProcessName, gbc);
            frame.setVisible(true);
        }

        panal.setBackground(new Color(255, 255, 255));
        JScrollPane panelPane = new JScrollPane(panal);
        panelPane.setBackground(new Color(255, 255, 255));
        frame.setBackground(new Color(255, 255, 255));
        frame.add(panelPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void AddColor(int row, int col, Color c) {
        JButton bu = new JButton();
        gbc.gridx = row;
        gbc.gridy = col;
        gbc.gridwidth = 1;
        bu.setBackground(c);
        panal.add(bu, gbc);
        frame.setVisible(true);
    }

    public void AddColor(int row, int col, Color c, int nExecutes) {
        for (int i = 0; i < nExecutes; i++) {
            JButton bu = new JButton();
            gbc.gridx = row + i;
            gbc.gridy = col;
            gbc.gridwidth = 1;
            bu.setOpaque(true);
            bu.setBorderPainted(false);
            bu.setBackground(c);
            panal.add(bu, gbc);
            frame.setVisible(true);
        }
    }
}