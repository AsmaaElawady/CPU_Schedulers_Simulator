import java.awt.Color;

 class Process  {
    protected String Name;
    protected Color ProcessColor; // will use it in GUI
    protected int BurstTime;
    protected int ArrivalTime;
    protected int Priority;
    protected int WaitingTime;
    protected int TurnaroundTime;
    protected int processingTime;
    protected int number;
    protected int StartTime;
    protected int LastTimeAged;
    protected String color;

    public Process() {

    }

    public Process(Process P) {
        Name = P.getName();
        BurstTime = P.getBurstTime();
        ArrivalTime = P.getArrivalTime();
        Priority = P.getPriority();
        ProcessColor = P.getColor();
        StartTime = -1;
        number = P.getNumber();
    }

    public Process(String Name, String clr, int ArrivalTime, int BurstTime, int Priority, int number) {
        this.Name = Name;
        this.BurstTime = BurstTime;
        this.ArrivalTime = ArrivalTime;
        this.Priority = Priority;
        this.number = number;
        StartTime = -1;
        //this.color = clr;
        LastTimeAged = ArrivalTime;// ***
        setColor(clr);
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    void execute() {
        System.out.println("Process " + Name);
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
                + "\nPriority : " + Priority + "\nWaiting Time : " + WaitingTime
                + "\nTurn Arround Time : " + TurnaroundTime);
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
            ProcessColor = (Color) field.get(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Color getColor() {
        return ProcessColor;
    }

   

    public int getLastTimeAged() {
        return LastTimeAged;
    }

    public void setLastTimeAged(int lastTimeAged) {
        LastTimeAged = lastTimeAged;
    }
    public int getProcessingTime() {
        return processingTime;
    }
     public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

}
