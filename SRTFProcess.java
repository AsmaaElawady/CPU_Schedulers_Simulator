import java.awt.Color;


public class SRTFProcess {

    private String name;
    private Color processColor; // will use it in GUI
    private int burstTime;
    private int arrivalTime;
    private int starvationChecker;
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;
    private int StartTime;
    private int LastTimeAged;


    public SRTFProcess(){
        
    }
    public SRTFProcess(String name, Color processColor, int burstTime, int arrivalTime) {
        this.name = name;
        this.processColor = processColor;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public void printProcess() {
        System.out.println("\nName : " + name + "\nArrival Time : " + arrivalTime + "\nBurstTime : " + burstTime
                + "\nWaiting Time : "+ waitingTime
                + "\nTurn Arround Time : " +  turnaroundTime);
    }

    public void setStarvationChecker(int starvationChecker) {
        this.starvationChecker = starvationChecker;
    }
    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Color getProcessColor() {
        return processColor;
    }


    public void setProcessColor(Color processColor) {
        this.processColor = processColor;
    }


    public int getBurstTime() {
        return burstTime;
    }


    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }


    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    public int getWaitingTime() {
        return waitingTime;
    }


    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }


    public int getTurnaroundTime() {
        return turnaroundTime;
    }


    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }


    public int getCompletionTime() {
        return completionTime;
    }


    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }


    public int getStartTime() {
        return StartTime;
    }


    public void setStartTime(int startTime) {
        StartTime = startTime;
    }


    public int getLastTimeAged() {
        return LastTimeAged;
    }


    public void setLastTimeAged(int lastTimeAged) {
        LastTimeAged = lastTimeAged;
    }

    public int getStarvationChecker() {
        return starvationChecker;
    }

    
    

    



}