import java.awt.Color;
import java.util.ArrayList;

public class Process implements Comparable<Process>
{
    private String Name;
    private Color ProcessColor; // will use it in GUI
    private int BurstTime;
    private int ArrivalTime;
    private int Priority;
    private int WaitingTime;
    private int TurnaroundTime;

    private int processingTime;

    private int StartTime;

    private int LastTimeAged;
    public Process(){

    }
    public Process(Process P)
    {
        Name =P.getName();
        BurstTime = P.getBurstTime();
        ArrivalTime = P.getArrivalTime();
        Priority = P.getPriority();
        ProcessColor = P.getColor();

        StartTime = -1;
    }

    public Process(String Name , String clr , int ArrivalTime , int BurstTime , int Priority)
    {
        this.Name = Name;
        this.BurstTime = BurstTime;
        this.ArrivalTime = ArrivalTime;
        this.Priority = Priority;

        StartTime = -1;

        LastTimeAged = ArrivalTime;//***

        setColor(clr);
    }

    void execute(){
        System.out.println( "Process " + Name );
        processingTime--;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getName() {
        return Name;
    }


    public void setBurstTime(int burstTime) {
        BurstTime = burstTime;
    }
    public int getBurstTime() {
        return BurstTime;
    }

    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
        LastTimeAged = ArrivalTime;
    }
    public int getArrivalTime() {
        return ArrivalTime;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }
    public int getPriority() {
        return Priority;
    }


    public void setWaitingTime(int waitingTime) {

        WaitingTime = waitingTime;
    }
    public int getWaitingTime() {
        return WaitingTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        TurnaroundTime = turnaroundTime;
    }

    public int getTurnaroundTime() {
        return TurnaroundTime;
    }
    public void printProcess() {
        System.out.println("\nName : " + Name + "\nArrival Time : " + ArrivalTime + "\nBurstTime : " + BurstTime
                + "\nPriority : " + Priority + "\nWaiting Time : "+ WaitingTime
                + "\nTurn Arround Time : " +  TurnaroundTime);
    }



    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public int getStartTime() {
        return StartTime;
    }


    public void setColor(String color) {

        java.lang.reflect.Field field = null;
        try {
            field = Class.forName("java.awt.Color").getField(color.toLowerCase());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } // toLowerCase because the color fields are RED or red, not Red
        try {
            ProcessColor = (Color)field.get(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Color getColor() {
        return ProcessColor;
    }
    @Override
    public int compareTo(Process o) {
        return this.getArrivalTime() - o.getArrivalTime();
    }

    public int getLastTimeAged() {
        return LastTimeAged;
    }

    public void setLastTimeAged(int lastTimeAged) {
        LastTimeAged = lastTimeAged;
    }



}
