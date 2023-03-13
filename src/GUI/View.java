package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class View extends JFrame {
    private final JFrame JframeSimulationSetUp;
    private final JFrame JframeQueuesManagement;
    private final JPanel contentPane2;
    private final JTextField textFieldSimTime;
    private final JTextField textFieldMinProcessingTime;
    private final JTextField textFieldMaxProcessingTime;
    private final JTextField textFieldMinArrivalTime;
    private final JTextField textFieldMaxArrivalTime;
    private final JTextField textFieldNumberOfQueues;
    private final JTextField textFieldMaxClientsPerQueue;
    private final JTextField textFieldNumberOfClients;
    private final JButton btnStartSimulation;
    private final JComboBox<String> strategyComboBox;
    private final JLabel lbDisplayPeakHour;
    private final JLabel lbDisplayCurrentTime;
    private final JLabel lbDisplayAverageWaitingTime;
    private final JLabel lbDisplayAverageServiceTime;
    private final JLabel lbDisplayWaitingClients;
    private List<JLabel> Queues;
    private boolean startApp = false;

    public View() {
        //Simulation Set Up
        JframeSimulationSetUp = new JFrame("Simulation Set Up");
        JframeSimulationSetUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JframeSimulationSetUp.setBounds(100, 100, 500, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel lbSimulationTime = new JLabel("Simulation Time");
        lbSimulationTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbSimulationTime.setBounds(28, 11, 106, 27);
        contentPane.add(lbSimulationTime);
        JLabel lbMinProcessingTime = new JLabel("Min Processing Time");
        lbMinProcessingTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbMinProcessingTime.setBounds(28, 49, 141, 27);
        contentPane.add(lbMinProcessingTime);
        JLabel lbMaxProcessingTime = new JLabel("Max Processing Time");
        lbMaxProcessingTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbMaxProcessingTime.setBounds(28, 87, 141, 27);
        contentPane.add(lbMaxProcessingTime);
        JLabel lbMinArrivalTime = new JLabel("Min Arrival Time");
        lbMinArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbMinArrivalTime.setBounds(28, 125, 106, 27);
        contentPane.add(lbMinArrivalTime);
        JLabel lbMaxArrivalTime = new JLabel("Max Arrival Time");
        lbMaxArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbMaxArrivalTime.setBounds(28, 163, 106, 27);
        contentPane.add(lbMaxArrivalTime);
        JLabel lbNrQueues = new JLabel("Number of Queues");
        lbNrQueues.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbNrQueues.setBounds(28, 201, 106, 27);
        contentPane.add(lbNrQueues);
        JLabel lbClientsPerQueue = new JLabel("Max Clients/Queue");
        lbClientsPerQueue.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbClientsPerQueue.setBounds(28, 239, 106, 27);
        contentPane.add(lbClientsPerQueue);
        JLabel lbSNrClients = new JLabel("Number of Clients");
        lbSNrClients.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbSNrClients.setBounds(28, 277, 162, 27);
        contentPane.add(lbSNrClients);
        strategyComboBox = new JComboBox<>();
        strategyComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"SHORTEST QUEUE", "SHORTEST TIME"}));
        strategyComboBox.setBounds(28, 315, 141, 22);
        contentPane.add(strategyComboBox);
        textFieldSimTime = new JTextField();
        textFieldSimTime.setText("60");
        textFieldSimTime.setBounds(227, 14, 86, 22);
        contentPane.add(textFieldSimTime);
        textFieldSimTime.setColumns(10);
        textFieldMinProcessingTime = new JTextField();
        textFieldMinProcessingTime.setText("1");
        textFieldMinProcessingTime.setColumns(10);
        textFieldMinProcessingTime.setBounds(227, 52, 86, 22);
        contentPane.add(textFieldMinProcessingTime);
        textFieldMaxProcessingTime = new JTextField();
        textFieldMaxProcessingTime.setText("7");
        textFieldMaxProcessingTime.setColumns(10);
        textFieldMaxProcessingTime.setBounds(227, 91, 86, 22);
        contentPane.add(textFieldMaxProcessingTime);
        textFieldMinArrivalTime = new JTextField();
        textFieldMinArrivalTime.setText("2");
        textFieldMinArrivalTime.setColumns(10);
        textFieldMinArrivalTime.setBounds(227, 130, 86, 22);
        contentPane.add(textFieldMinArrivalTime);
        textFieldMaxArrivalTime = new JTextField();
        textFieldMaxArrivalTime.setText("40");
        textFieldMaxArrivalTime.setColumns(10);
        textFieldMaxArrivalTime.setBounds(227, 167, 86, 22);
        contentPane.add(textFieldMaxArrivalTime);
        textFieldNumberOfQueues = new JTextField();
        textFieldNumberOfQueues.setText("5");
        textFieldNumberOfQueues.setColumns(10);
        textFieldNumberOfQueues.setBounds(227, 205, 86, 22);
        contentPane.add(textFieldNumberOfQueues);
        textFieldMaxClientsPerQueue = new JTextField();
        textFieldMaxClientsPerQueue.setText("20");
        textFieldMaxClientsPerQueue.setColumns(10);
        textFieldMaxClientsPerQueue.setBounds(227, 243, 86, 22);
        contentPane.add(textFieldMaxClientsPerQueue);
        textFieldNumberOfClients = new JTextField();
        textFieldNumberOfClients.setText("50");
        textFieldNumberOfClients.setColumns(10);
        textFieldNumberOfClients.setBounds(227, 281, 86, 22);
        contentPane.add(textFieldNumberOfClients);
        btnStartSimulation = new JButton("START SIMULATION");
        btnStartSimulation.setBounds(323, 160, 151, 34);
        contentPane.add(btnStartSimulation);
        JframeSimulationSetUp.add(contentPane);
        JframeSimulationSetUp.setVisible(true);
        //Real Time Queues Management
        JframeQueuesManagement = new JFrame("Real Time Queues Management");
        JframeQueuesManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JframeQueuesManagement.setBounds(100, 100, 800, 600);
        contentPane2 = new JPanel();
        contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane2);
        contentPane2.setLayout(null);
        JLabel lbCurrentTime = new JLabel("Current Time:");
        lbCurrentTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbCurrentTime.setBounds(10, 11, 111, 29);
        contentPane2.add(lbCurrentTime);
        lbDisplayCurrentTime = new JLabel("....");
        lbDisplayCurrentTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbDisplayCurrentTime.setBounds(158, 15, 57, 21);
        contentPane2.add(lbDisplayCurrentTime);
        JLabel lbAverageWaitingTime = new JLabel("Average Waiting Time:");
        lbAverageWaitingTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbAverageWaitingTime.setBounds(279, 11, 140, 29);
        contentPane2.add(lbAverageWaitingTime);
        lbDisplayAverageWaitingTime = new JLabel("....");
        lbDisplayAverageWaitingTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbDisplayAverageWaitingTime.setBounds(443, 11, 111, 29);
        contentPane2.add(lbDisplayAverageWaitingTime);
        JLabel lbAverageServiceTime = new JLabel("Average Service Time:");
        lbAverageServiceTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbAverageServiceTime.setBounds(10, 41, 149, 29);
        contentPane2.add(lbAverageServiceTime);
        lbDisplayAverageServiceTime = new JLabel("....");
        lbDisplayAverageServiceTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbDisplayAverageServiceTime.setBounds(158, 41, 111, 29);
        contentPane2.add(lbDisplayAverageServiceTime);
        JLabel lbPeakHour = new JLabel("Peak Hour:");
        lbPeakHour.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbPeakHour.setBounds(279, 41, 111, 29);
        contentPane2.add(lbPeakHour);
        lbDisplayPeakHour = new JLabel("....");
        lbDisplayPeakHour.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbDisplayPeakHour.setBounds(443, 41, 111, 29);
        contentPane2.add(lbDisplayPeakHour);
        JframeQueuesManagement.setVisible(false);
        JLabel lbWaitingClients = new JLabel("Waiting Clients:");
        lbWaitingClients.setHorizontalAlignment(SwingConstants.LEFT);
        lbWaitingClients.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbWaitingClients.setBounds(10, 81, 111, 14);
        contentPane2.add(lbWaitingClients);
        lbDisplayWaitingClients = new JLabel("...");
        lbDisplayWaitingClients.setHorizontalAlignment(SwingConstants.LEFT);
        lbDisplayWaitingClients.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbDisplayWaitingClients.setBounds(113, 82, 650, 14);
        contentPane2.add(lbDisplayWaitingClients);
    }

    public void addQueuesLabel() {
        int sizeOfQueues = Math.abs(Integer.parseInt(getTextFieldNumberOfQueues()));
        Queues = new ArrayList<>(sizeOfQueues);
        for (int i = 0; i < sizeOfQueues; i++) {
            JLabel queue = new JLabel();
            queue.setFont(new Font("Tahoma", Font.PLAIN, 12));
            queue.setBounds(10, (106 + i * 16), 780, 14);
            Queues.add(queue);
        }
        for (JLabel label : Queues)
            contentPane2.add(label);
        JframeQueuesManagement.add(contentPane2);
    }

    public void setLbDisplayWaitingClients(String time) {
        lbDisplayWaitingClients.setText(time);
    }

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    public void setLbDisplayAverageWaitingTime(String time) {
        lbDisplayAverageWaitingTime.setText(time);
    }

    public void setLbDisplayAverageServiceTime(String time) {
        lbDisplayAverageServiceTime.setText(time);
    }

    public void setLbDisplayPeakHour(String peak) {
        lbDisplayPeakHour.setText(peak);
    }

    public synchronized boolean getStart() {
        return startApp;
    }

    public synchronized void setStart(boolean start) {
        startApp = start;
    }

    public void setLbDisplayCurrentTime(int CurrentTime) {
        this.lbDisplayCurrentTime.setText("" + CurrentTime);
    }

    public List<JLabel> getQueuesLabels() {
        return Queues;
    }

    public String getStrategyComboBox() {
        return Objects.requireNonNull(strategyComboBox.getSelectedItem()).toString();
    }

    public void setJframeQueuesManagement(boolean visible) {
        JframeQueuesManagement.setVisible(visible);
        JframeSimulationSetUp.setVisible(!visible);
    }

    public String getTextFieldSimTime() {
        return textFieldSimTime.getText();
    }

    public String getTextFieldMinProcessingTime() {
        return textFieldMinProcessingTime.getText();
    }

    public String getTextFieldMaxProcessingTime() {
        return textFieldMaxProcessingTime.getText();
    }

    public String getTextFieldMinArrivalTime() {
        return textFieldMinArrivalTime.getText();
    }

    public String getTextFieldMaxArrivalTime() {
        return textFieldMaxArrivalTime.getText();
    }

    public String getTextFieldNumberOfQueues() {
        return textFieldNumberOfQueues.getText();
    }

    public String getTextFieldMaxClientsPerQueue() {
        return textFieldMaxClientsPerQueue.getText();
    }

    public String getTextFieldNumberOfClients() {
        return textFieldNumberOfClients.getText();
    }

    public void addStartSimulationButtonListener(ActionListener action) {
        btnStartSimulation.addActionListener(action);
    }

    public JButton getBtnStartSimulation() {
        return btnStartSimulation;
    }
}
