package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private static int totalTime;
    private final List<Server> servers;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxClientsPerServer, SelectionPolicy selectionPolicy) {
        changeStrategy(selectionPolicy);
        servers = new ArrayList<>(maxNoServers);
        for (int i = 0; i < maxNoServers; i++) {
            servers.add(new Server(maxClientsPerServer));
        }
    }

    public static int getTotalTime() {
        return totalTime;
    }

    public static void addAverageTime(int addedTime) {
        totalTime += addedTime;
    }


    private void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();
        else strategy = new ShortestTimeStrategy();
    }

    public synchronized void dispatchClient(Client client) {
        strategy.addClient(servers, client);
    }

    public List<Server> getServers() {
        return servers;
    }

    public boolean checkAllClosedQueues() {
        for (Server server : servers)
            if (server.getClients().size() > 0)
                return true;
        return false;
    }
}
