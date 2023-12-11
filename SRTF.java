import java.util.*;

class arrivalCompartor implements Comparator<SRTFProcess>{

    @Override
    public int compare(SRTFProcess o1, SRTFProcess o2) {
        if (o1.getArrivalTime() == o2.getArrivalTime()) {
            return o1.getBurstTime() - o2.getBurstTime();
        }else {
            return o1.getArrivalTime() - o2.getArrivalTime();
        }
    }

}

class brustCompartor implements Comparator<SRTFProcess>{

    @Override
    public int compare(SRTFProcess o1, SRTFProcess o2) {
        
            return o1.getBurstTime() - o2.getBurstTime();
        
    }

}


public class SRTF {

    ArrayList<SRTFProcess> processesList;

    public SRTF(ArrayList<SRTFProcess> processesList) {
        this.processesList = processesList;
    }


    public void startProcessing(){
        PriorityQueue<SRTFProcess> pq = new PriorityQueue<>(processesList.size(),new arrivalCompartor());

        // adding the processes and sort them refering to the brust time and arrival time;
        for (SRTFProcess srtfProcess : pq) {
            pq.add(srtfProcess);
            
        }

        //setting the starvationChecker for each process
        for (SRTFProcess srtfProcess : processesList) {
            srtfProcess.setStarvationChecker(srtfProcess.getBurstTime());
        }

        int inCpuTime = 0;
         //to take the first process
             while (true) {
                if (pq.peek().getArrivalTime() > inCpuTime) {
                    inCpuTime++;
                }else{
                    break;
                }
             }

             SRTFProcess readyProcess = pq.peek();
             inCpuTime ++;
             runProcess(readyProcess,inCpuTime); //inCpuTime works as completionTime
             System.out.println(readyProcess.getName() + " processing " + "btime :"+ readyProcess.getBurstTime());
        
        
             while (pq.size() > 0) {

            //will add to it the arrived processes sorted with the brust time
            PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(new brustCompartor());
           
            for (SRTFProcess p : pq) {
                // if process arrived 
               if (p.getArrivalTime() >= inCpuTime) {
                    readyQueue.add(p);
                }
            }
            inCpuTime++;
            runProcess(readyQueue.peek(), inCpuTime);
             System.out.println(readyQueue.peek() + " processing " + "btime :"+ readyQueue.peek().getBurstTime());

            if (readyQueue.peek().getBurstTime() == 0 ) {
                pq.remove(readyQueue.peek());
                System.out.println(readyQueue.peek() + " finished " + "btime :"+ readyQueue.peek().getBurstTime());

            }


            
                
            }
           
            
             
           
        }

       
    

    public void runProcess(SRTFProcess process,int compTime){
        int bTime = process.getBurstTime();
        bTime--;
        process.setBurstTime(bTime);
        process.setCompletionTime(compTime);

    }

    public ArrayList<SRTFProcess> getProcessesList() {
        return processesList;
    }

    public void setProcessesList(ArrayList<SRTFProcess> processesList) {
        this.processesList = processesList;
    }
    
    




    
}
