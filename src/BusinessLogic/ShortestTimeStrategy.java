package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {
    private Server goServer;

    public synchronized void addClient(List<Server> servers, Client client) {
        int minTime = 2147483640;
        for (Server curServer : servers) {
            if (curServer.getQueueWaitingTime().get() < minTime) {
                minTime = curServer.getQueueWaitingTime().get();
                goServer = curServer;
            }
        }
        goServer.addClient(client);
        Scheduler.addAverageTime(goServer.getQueueWaitingTime().intValue());
    }
}
