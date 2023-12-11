import java.util.ArrayList;

public class AG {

    ArrayList<AGprocess> processesList;
    ArrayList<AGprocess> dieList;

    
    public AG(ArrayList<Process> processes, int quantm) {
        this.processesList = new ArrayList<AGprocess>(processes.size());
        for (int i = 0; i < processes.size(); i++) {
            AGprocess temp = new AGprocess();
            temp.setName(processes.get(i).getName());
            temp.setNumber(processes.get(i).getNumber());
            //temp.setColor(processes.get(i).getColor());
            temp.setArrivalTime(processes.get(i).getArrivalTime());
            temp.setBurstTime(processes.get(i).getBurstTime());
            temp.setQuantm(quantm);
            this.processesList.add(temp) ; 
        }
    }



    public void startProcessing(){
        // will start processing criteria here
    }

    public void makeAgFactor(){
        for (AGprocess proc : processesList) {
            int agFac = 0 , randNum;
            randNum = (int) (Math.random() * 21); // creates number betweem 0->20

            if (randNum < 10) {
                agFac = randNum + proc.getArrivalTime() + proc.getBurstTime();
            }else if (randNum > 10){
                agFac = 10 + proc.getArrivalTime() + proc.getBurstTime();
            }else if (randNum == 10 ){
                agFac = proc.getPriority() + proc.getArrivalTime() + proc.getBurstTime();
            }

            proc.setAgFactor(agFac);
        }
    }

    public void runProcess(AGprocess process , int compTime){
        int bTime = process.getBurstTime();
        bTime--;
        process.setBurstTime(bTime);
        process.setProcessingTime(compTime);
    }

    




    
}
