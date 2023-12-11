import java.awt.Color;


public class SRTFProcess  extends Process{

    
    private int starvationChecker;
   


    public SRTFProcess(){

    }
    public SRTFProcess(String name, Color processColor, int burstTime, int arrivalTime) {
       super();
    }

    

    public void setStarvationChecker(int starvationChecker) {
        this.starvationChecker = starvationChecker;
    }
    
    public int getStarvationChecker() {
        return starvationChecker;
    }

    
    

    



}