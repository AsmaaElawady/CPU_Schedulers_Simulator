public class AGprocess extends Process {
    private int agFactor;
    private int quantm;
    private int tempBurst;
   
    public AGprocess(){}

    public AGprocess(String Name, String clr, int ArrivalTime, int BurstTime, int Priority, int number){
        super();
    }

    public int getAgFactor() {
        return agFactor;
    }

    public void setAgFactor(int agFactor) {
        this.agFactor = agFactor;
    }
    
    public int getQuantm() {
        return quantm;
    }

    public void setQuantm(int quantm) {
        this.quantm = quantm;
    }

    public int getTempBurst() {
        return tempBurst;
    }

    public void setTempBurst(int tempBurst) {
        this.tempBurst = tempBurst;
    }
}
