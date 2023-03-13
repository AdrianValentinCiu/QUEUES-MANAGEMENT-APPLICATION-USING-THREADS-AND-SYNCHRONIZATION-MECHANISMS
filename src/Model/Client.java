package Model;

public class Client {
    private final int id;
    private final int arrivalTime;
    private final int serviceTime;
    private int curServiceTime;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.curServiceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void decreaseWaitingTime() {
        curServiceTime--;
    }

    public int getCurServiceTime() {
        return curServiceTime;
    }

    public synchronized String toString() {
        return "(" + id + ", " + arrivalTime + ", " + curServiceTime + "); ";
    }

}

