import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SchedulingGUI {
    JFrame frame = new JFrame("CPU Schedulers");
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel panel = new JPanel();
    ArrayList<Process> Processes = new ArrayList<Process>();
    JTable table;
    DefaultTableModel model;
    JTextField contextSwitchField;
    JTextField quantumTimeField;
    JTextField processNameField;
    JTextField arrivalTimeField;
    JTextField burstTimeField;
    JTextField priorityField;
    JTextField colorField;
    JComboBox<String> schedulingTechnique;
    JButton addButton;
    JButton startButton;
    int contextSwitch;

    public SchedulingGUI() {
        panel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Context Switch and Quantum Time Fields
        contextSwitchField = new JTextField(5);
        quantumTimeField = new JTextField(5);
        addLabelAndField("Context Switch:", contextSwitchField, 0, 0);
        addLabelAndField("RR Time Quantum:", quantumTimeField, 0, 1);

        // Process Details Fields
        processNameField = new JTextField(10);
        arrivalTimeField = new JTextField(5);
        burstTimeField = new JTextField(5);
        priorityField = new JTextField(5);
        colorField = new JTextField(5);
        addButton = new JButton("Add Process");
        addButton.addActionListener(e -> addProcessToTable());
        addLabelAndField("Process Name:", processNameField, 0, 2);
        addLabelAndField("Arrival Time:", arrivalTimeField, 2, 2);
        addLabelAndField("Burst Time:", burstTimeField, 4, 2);
        addLabelAndField("Priority:", priorityField, 6, 2);
        addLabelAndField("Color:", colorField, 8, 2);
        gbc.gridwidth = 2;
        gbc.gridx = 10;
        gbc.gridy = 2;
        panel.add(addButton, gbc);

        // Table to Display Processes
        String[] columns = { "Process Name", "Arrival Time", "Burst Time", "Priority", "Color", "Waiting Time", "TAT" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridwidth = 12;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(scrollPane, gbc);

        // Scheduling Technique Selection
        String[] techniques = { "SJF", "SRTF", "Priority", "AG" };
        schedulingTechnique = new JComboBox<>(techniques);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(schedulingTechnique, gbc);

        // Start Button
        startButton = new JButton("Start Scheduling");
        startButton.addActionListener(e -> startScheduling());
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(startButton, gbc);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addLabelAndField(String label, JTextField textField, int x, int y) {
        JLabel jLabel = new JLabel(label);
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(jLabel, gbc);

        gbc.gridx = x + 1;
        gbc.gridy = y;
        panel.add(textField, gbc);
    }

    private void addProcessToTable() {
        String processName = processNameField.getText();
        int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
        int burstTime = Integer.parseInt(burstTimeField.getText());
        int priority = Integer.parseInt(priorityField.getText());
        String color = colorField.getText();

        Process process = new Process(processName, color, arrivalTime, burstTime, priority, Processes.size());
        Processes.add(process);

        // Add process details to the table
        Object[] row = { processName, arrivalTime, burstTime, priority, color, "", "" };
        model.addRow(row);
    }

    // Method to update table row with waiting time and TAT
    public void updateTableRow(int row, String columnName, double value) {
        table.setValueAt(value, row, getColumnIndex(columnName));
    }

    // Helper method to get the column index by name
    private int getColumnIndex(String columnName) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    public void startScheduling() {
        contextSwitch = Integer.parseInt(contextSwitchField.getText());
        String technique = (String) schedulingTechnique.getSelectedItem();
        ChartGUI chart = new ChartGUI(Processes);
        switch (technique) {
            case "SJF":
                SJF sjf = new SJF(Processes, contextSwitch, chart, SchedulingGUI.this);
                break;
            case "SRTF":
                SRTF srtf = new SRTF(Processes, chart, SchedulingGUI.this);
                srtf.startProcessing();
                break;
            default:
                break;
        }
    }

    public void addRow(Object[] row){
        model.addRow(row);
    }

    public static void main(String[] args) {
        new SchedulingGUI();
    }
}
