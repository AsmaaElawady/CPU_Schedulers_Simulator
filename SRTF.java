import java.util.*;

class arrivalCompartor implements Comparator<SRTFProcess> {

    @Override
    public int compare(SRTFProcess o1, SRTFProcess o2) {
        if (o1.getArrivalTime() == o2.getArrivalTime()) {
            return o1.getBurstTime() - o2.getBurstTime();
        } else {
            return o1.getArrivalTime() - o2.getArrivalTime();
        }
    }

}

class brustCompartor implements Comparator<SRTFProcess> {

    @Override
    public int compare(SRTFProcess o1, SRTFProcess o2) {

        return o1.getStarvationChecker() - o2.getStarvationChecker();

    }

}

public class SRTF {

    ArrayList<SRTFProcess> processesList;
    double agingFactor = 0.5;

    public SRTF(ArrayList<Process> processes) {
        
        this.processesList = new ArrayList<SRTFProcess>(processes.size());
        for (int i = 0; i < processes.size(); i++) {
            SRTFProcess temp = new SRTFProcess();
            temp.setName(processes.get(i).getName());
            temp.setNumber(processes.get(i).getNumber());
            //temp.setColor(processes.get(i).getColor());
            temp.setArrivalTime(processes.get(i).getArrivalTime());
            temp.setBurstTime(processes.get(i).getBurstTime());
            this.processesList.add(temp) ; 
        }
    }

    public void startProcessing() {
        PriorityQueue<SRTFProcess> pq = new PriorityQueue<>(processesList.size(), new arrivalCompartor());

        // adding the processes and sort them refering to the brust time and arrival
        // time;
        for (SRTFProcess srtfProcess : processesList) {
            pq.add(srtfProcess);

        }
        

        // setting the starvationChecker for each process
        for (SRTFProcess srtfProcess : processesList) {
            
            srtfProcess.setStarvationChecker(srtfProcess.getBurstTime());
        }

        int inCpuTime = 0;
    

        while (pq.size() > 0) {

            // will add to it the arrived processes sorted with the brust time
            PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(new brustCompartor());

            while(readyQueue.size() < 1){
                for (SRTFProcess p : pq) {
                // if process arrived
                if (p.getArrivalTime() <= inCpuTime) {
                    readyQueue.add(p);
                }
                }
                if (readyQueue.size() == 0) {
                    inCpuTime++; 
                }
            }
            

           
            inCpuTime++;
            runProcess(readyQueue.peek(), inCpuTime);
            System.out.println(readyQueue.peek().getName() + " processing " + "btime :" + readyQueue.peek().getBurstTime());

            if (readyQueue.peek().getBurstTime() == 0) {
                pq.remove(readyQueue.peek());
                System.out.println(readyQueue.peek().getName() + " finished " + "compeletionT :" + readyQueue.peek().getProcessingTime());

            }

            for (SRTFProcess proc: pq) {
                int s = proc.getStarvationChecker();
                s -=  (inCpuTime - proc.getArrivalTime()) * agingFactor;
                proc.setStarvationChecker(s);
            }

        }

    }

    public void runProcess(SRTFProcess process, int compTime) {
        int bTime = process.getBurstTime();
        bTime--;
        process.setBurstTime(bTime);
        process.setProcessingTime(compTime);

    }

    public ArrayList<SRTFProcess> getProcessesList() {
        return processesList;
    }

    public void setProcessesList(ArrayList<SRTFProcess> processesList) {
        this.processesList = processesList;
    }

}
