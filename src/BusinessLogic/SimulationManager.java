package BusinessLogic;

import GUI.View;
import Model.Client;
import Model.Server;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class SimulationManager implements Runnable {
    private final View readDisplayData;
    private FileWriter logFile;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final ClientSort clientSort;
    private int peakHour = 0;
    private int maxQueue = 0;
    private int timeLimit = 0;
    private int minProcessingTime = 0;
    private int maxProcessingTime = 0;
    private int minArrivalTime = 0;
    private int maxArrivalTime = 0;
    private int numberOfClients = 0;
    private int maxClientsPerServer = 0;
    private int numberOfServers = 0;
    private final Scheduler scheduler;
    private final LinkedList<Client> generatedClients;
    private int clientsServer;

    public SimulationManager() {
        readDisplayData = new View();
        getData();
        readDisplayData.addQueuesLabel();
        SelectionPolicy selectionPolicy;
        if (readDisplayData.getStrategyComboBox().equals("SHORTEST QUEUE"))
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
        else selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        try {
            logFile = new FileWriter("log.txt");
        } catch (IOException e) {
            System.out.println("The log file could not be opened");
        }
        scheduler = new Scheduler(numberOfServers, maxClientsPerServer, selectionPolicy);
        generatedClients = new LinkedList<>();
        clientSort = new ClientSort();
        generateRandomClients();
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            scheduler.getServers().get(i).setName("Q" + (i + 1));
            scheduler.getServers().get(i).start();
        }
    }

    public void getData() {
        readDisplayData.setStart(false);
        while (!readDisplayData.getStart()) {
            readDisplayData.addStartSimulationButtonListener(e -> {
                if (e.getSource() == readDisplayData.getBtnStartSimulation()) {
                    readDisplayData.setStart(true);
                }
            });
        }
        try {
            this.timeLimit = Math.abs(Integer.parseInt(readDisplayData.getTextFieldSimTime()));
            this.minProcessingTime = Math.abs(Integer.parseInt(readDisplayData.getTextFieldMinProcessingTime()));
            this.maxProcessingTime = Math.abs(Integer.parseInt(readDisplayData.getTextFieldMaxProcessingTime()));
            this.minArrivalTime = Math.abs(Integer.parseInt(readDisplayData.getTextFieldMinArrivalTime()));
            this.maxArrivalTime = Math.abs(Integer.parseInt(readDisplayData.getTextFieldMaxArrivalTime()));
            this.numberOfServers = Math.abs(Integer.parseInt(readDisplayData.getTextFieldNumberOfQueues()));
            this.numberOfClients = Math.abs(Integer.parseInt(readDisplayData.getTextFieldNumberOfClients()));
            this.maxClientsPerServer = Math.abs(Integer.parseInt(readDisplayData.getTextFieldMaxClientsPerQueue()));
            if (minArrivalTime > maxArrivalTime || minProcessingTime > maxProcessingTime)
                throw new Exception();
        } catch (Exception e) {
            readDisplayData.showError("Invalid input!");
            getData();
        }
    }

    public void generateRandomClients() {
        Random r = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            generatedClients.add(new Client(i, r.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime, r.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime));
        }
        generatedClients.sort(clientSort);
    }

    public void writeLogData(String text) {
        System.out.print(text);
        try {
            logFile.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getServiceTime() {
        int serviceTime = 0;
        for (Client client : generatedClients)
            serviceTime += client.getServiceTime();
        if (serviceTime > 0)
            return serviceTime;
        else return 1;
    }

    public void closeLogFile() {
        try {
            logFile.close();
        } catch (IOException ignored) {
        }
    }

    public void processClient(int currentTime) {
        try {
            while (generatedClients.getFirst().getArrivalTime() <= currentTime && checkFullQueues()) {
                scheduler.dispatchClient(generatedClients.pop());
                clientsServer++;
            }
        } catch (Exception ignored) {
        }
    }

    public void closeServers() {
        int i = 0;
        for (Server server : scheduler.getServers()) {
            readDisplayData.getQueuesLabels().get(i++).setText(server.getName() + ": " + server);
            writeLogData(server.getName() + ": " + server + "\n");
            server.stopQueue(true);
        }
    }

    public boolean checkFullQueues() {
        for (Server server : scheduler.getServers())
            if (server.getClients().size() < maxClientsPerServer)
                return true;
        return false;
    }

    @Override
    public void run() {
        int currentTime = 0, serviceTime = getServiceTime();
        readDisplayData.setJframeQueuesManagement(true);
        while (currentTime <= timeLimit || scheduler.checkAllClosedQueues()) {
            readDisplayData.setLbDisplayCurrentTime(currentTime);
            writeLogData("------------------------------------------------------------------\n" + "TIME: " + currentTime + "\n");
            if (currentTime < timeLimit)
                processClient(currentTime);
            writeLogData(generatedClients.toString());
            writeLogData("\n");
            readDisplayData.setLbDisplayWaitingClients(generatedClients.toString());
            int i = 0;
            for (Server server : scheduler.getServers()) {
                if (maxQueue < server.getClients().size()) {
                    maxQueue = server.getClients().size();
                    peakHour = currentTime;
                }
                readDisplayData.getQueuesLabels().get(i++).setText(server.getName() + ": " + server);
                writeLogData(server.getName() + ": " + server + "\n");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            currentTime++;
        }
        writeLogData("------------------------------------------------------------------\n");
        closeServers();
        writeLogData("Average waiting time: " + (df.format(Scheduler.getTotalTime() / (double) clientsServer)) + "\n");
        writeLogData("Average service time: " + (df.format(serviceTime / (double) clientsServer) + "\n" + "Peak Hour: " + peakHour + "\nSimulation ended!"));
        closeLogFile();
        readDisplayData.setLbDisplayAverageWaitingTime(df.format(Scheduler.getTotalTime() / (double) clientsServer));
        readDisplayData.setLbDisplayAverageServiceTime(df.format(serviceTime / (double) clientsServer) + "\n");
        readDisplayData.setLbDisplayPeakHour("" + peakHour);
    }

    public static void main(String[] args) {
        SimulationManager sim = new SimulationManager();
        Thread app = new Thread(sim);
        app.start();
    }
}

class ClientSort implements Comparator<Client> {
    public int compare(Client t1, Client t2) {
        return t1.getArrivalTime() - t2.getArrivalTime();
    }
}

