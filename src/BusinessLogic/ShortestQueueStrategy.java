package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    private Server goServer;

    public synchronized void addClient(List<Server> servers, Client client) {
        int minQueue = 2147483640;
        for (Server curServer : servers) {
            if (curServer.getClients().size() < minQueue) {
                minQueue = curServer.getClients().size();
                goServer = curServer;
            }
        }
        goServer.addClient(client);
        Scheduler.addAverageTime(goServer.getQueueWaitingTime().intValue());
    }
}
