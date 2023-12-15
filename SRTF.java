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

class burstCompartor implements Comparator<SRTFProcess> {

    @Override
    public int compare(SRTFProcess o1, SRTFProcess o2) {

        return o1.getStarvationChecker() - o2.getStarvationChecker();

    }

}

public class SRTF {

    ArrayList<SRTFProcess> processesList;
    double agingFactor = 0;
    ChartGUI chart; // to visualize the order of precesses in cpu.
    SchedulingGUI schedulingGUI; // to show the avg time and TAT for each process.

    public SRTF(ArrayList<Process> processes, ChartGUI chart, SchedulingGUI schedulingGUI) {
        this.chart = chart;
        this.schedulingGUI = schedulingGUI;
        this.processesList = new ArrayList<SRTFProcess>(processes.size());
        
        for (int i = 0; i < processes.size(); i++) {
            SRTFProcess temp = new SRTFProcess();
            temp.setName(processes.get(i).getName());
            temp.setNumber(processes.get(i).getNumber());
            //temp.setColor(processes.get(i).color);
            temp.setArrivalTime(processes.get(i).getArrivalTime());
            temp.setBurstTime(processes.get(i).getBurstTime());
            temp.setTempBurst(processes.get(i).getBurstTime());
            this.processesList.add(temp) ; 
        }
    }

    public SRTF(ArrayList<Process> processes) {
        
        this.processesList = new ArrayList<SRTFProcess>(processes.size());
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            SRTFProcess temp = new SRTFProcess(p.getName(),p.color,p.getArrivalTime(),p.getBurstTime(),p.getNumber());
            this.processesList.add(temp);

            // temp.setName(processes.get(i).getName());
            // temp.setNumber(processes.get(i).getNumber());
            // // String c = processes.get(i).color.toUpperCase();
            // // temp.setColor(c);
            // temp.setArrivalTime(processes.get(i).getArrivalTime());
            // temp.setBurstTime(processes.get(i).getBurstTime());
            // temp.setTempBurst(processes.get(i).getBurstTime());
            // this.processesList.add(temp) ; 
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


        double avrWaiting = 0, avrTAT = 0 ;
        int  inCpuTime = 0 , nProcess = pq.size();

        while (pq.size() > 0) {

            // will add to it the arrived processes sorted with the brust time
            PriorityQueue<SRTFProcess> readyQueue = new PriorityQueue<>(new burstCompartor());

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
            

           // waiting time = (start time - arrival time) 
           // TAT = waiting time + burst time.
            
            inCpuTime++;
            runProcess(readyQueue.peek(), inCpuTime);

            System.out.println(readyQueue.peek().getName() + " processing " + "btime :" + readyQueue.peek().getBurstTime());

            if (readyQueue.peek().getBurstTime() == 0) {
                SRTFProcess s = readyQueue.peek();
                readyQueue.peek().setTurnaroundTime(readyQueue.peek().getProcessingTime() - readyQueue.peek().getArrivalTime());
                readyQueue.peek().setWaitingTime(readyQueue.peek().getTurnaroundTime() - readyQueue.peek().getTempBurst());
               
                int row = readyQueue.peek().getNumber();
                //Gui updating part 
                this.schedulingGUI.updateTableRow(row, "Waiting Time", s.getWaitingTime());
                this.schedulingGUI.updateTableRow(row, "TAT", s.getTurnaroundTime());


                avrWaiting += readyQueue.peek().getWaitingTime();
                avrTAT += readyQueue.peek().getTurnaroundTime();
                
                pq.remove(readyQueue.peek());
                System.out.println(readyQueue.peek().getName() + " finished " + "compeletionT :" + readyQueue.peek().getProcessingTime() + "  w: " + readyQueue.peek().getWaitingTime() + " tat: " + readyQueue.peek().getTurnaroundTime());

            }

            for (SRTFProcess proc: pq) {
                int s = proc.getStarvationChecker();
                s -=  (inCpuTime - proc.getArrivalTime()) * agingFactor;
                proc.setStarvationChecker(s);
            }

        }

        avrWaiting /= nProcess;
        avrTAT /= nProcess;
        Object[] avgWaiting = {"Average waiting time", avrWaiting};
        this.schedulingGUI.addRow(avgWaiting);
        System.out.println("Average waiting time: " + avrWaiting);
        Object[] avgTATRow = {"Average TAT time", avrTAT};
        this.schedulingGUI.addRow(avgTATRow);
        System.out.println("Average TAT: " + avrTAT);

    }

    public void runProcess(SRTFProcess process, int compTime) {
        int bTime = process.getBurstTime();
        this.chart.AddColor(compTime, process.getNumber(), process.getColor(), process.getBurstTime());
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
