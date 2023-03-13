package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server extends Thread {
    private final BlockingQueue<Client> clients;
    private final AtomicInteger queueWaitingTime;
    private boolean run;

    public Server(int maxTasksPerServer) {
        clients = new LinkedBlockingQueue<>(maxTasksPerServer);
        queueWaitingTime = new AtomicInteger(0);
    }

    public void run() {
        while (!run) {
            if (!clients.isEmpty()) {
                Client curClient = clients.peek();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                if (curClient.getCurServiceTime() > 1) {
                    curClient.decreaseWaitingTime();
                    deceaseQueueWaitingTime();
                } else {
                    clients.poll();
                }
            }
        }
    }

    public synchronized void addClient(Client newClient) {
        clients.add(newClient);
        queueWaitingTime.addAndGet(newClient.getServiceTime());
    }

    public void deceaseQueueWaitingTime() {
        queueWaitingTime.addAndGet(-1);
    }

    public AtomicInteger getQueueWaitingTime() {
        return queueWaitingTime;
    }

    public BlockingQueue<Client> getClients() { // public Task[] getTasks{}
        return clients;
    }

    public void stopQueue(boolean run) {
        this.run = run;
    }

    public String toString() {
        StringBuilder allClients = new StringBuilder();
        if (clients.isEmpty())
            return "closed";
        for (Client client : clients) {
            allClients.append(client.toString());
        }
        return allClients.toString();
    }

}

