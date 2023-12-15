import java.awt.Color;


public class SRTFProcess  extends Process{

    
    private int starvationChecker;
    private int tempBurst;


   
    public SRTFProcess(){

    }
    public SRTFProcess(String Name, String clr, int ArrivalTime, int BurstTime, int number) {
       super();
    }

    
    public int getTempBurst() {
        return tempBurst;
    }
    public void setTempBurst(int tempBurst) {
        this.tempBurst = tempBurst;
    }
    public void setStarvationChecker(int starvationChecker) {
        this.starvationChecker = starvationChecker;
    }
    
    public int getStarvationChecker() {
        return starvationChecker;
    }

    
    

    



}