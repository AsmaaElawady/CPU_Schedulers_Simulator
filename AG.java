import java.util.*;

public class AG {
    ArrayList<AGprocess> processesList;
    ArrayList<AGprocess> dieList = new ArrayList<>();
    Queue<AGprocess> readyQueue = new LinkedList<>();
    ChartGUI chart;
    SchedulingGUI schedulingGUI;

    public AG(ChartGUI chart, SchedulingGUI schedulingGUI, ArrayList<Process> processes, int quantm) {
        this.chart = chart;
        this.schedulingGUI = schedulingGUI;
        this.processesList = new ArrayList<AGprocess>(processes.size());

        for (int i = 0; i < processes.size(); i++) {
            AGprocess temp = new AGprocess();
            temp.setName(processes.get(i).getName());
            temp.setNumber(processes.get(i).getNumber());
            temp.setArrivalTime(processes.get(i).getArrivalTime());
            temp.setBurstTime(processes.get(i).getBurstTime());
            temp.setTempBurst(processes.get(i).getBurstTime());
            temp.setPriority(processes.get(i).getPriority());
            temp.setQuantm(quantm);
            temp.setColor(processes.get(i).getColorString());
            this.processesList.add(temp);
        }

        makeAgFactor();
        // sort the processes according to the arrival time.
        Collections.sort(processesList, Comparator.comparing(Process::getArrivalTime));

        printProcesses();
    }

    public void startProcessing() {
        double halfQTime;
        double avrTAT  = 0, avrWaiting = 0;
        int nProcess = processesList.size(), totalTime = 0;

        AGprocess currProcess = processesList.get(0);
        while (processesList.size() > 0) {
            System.out.println("process " + currProcess.getName() + " is now in cpu.");

            // calculate the time the process will be non preemptive in it, after this time the process will be preemptive.
            halfQTime = Math.ceil(currProcess.getQuantm() / 2.0);
            System.out.println("half time for process " + currProcess.getName() + " " + halfQTime);
            
            System.out.println("processes in ready queue now: ");
            if (readyQueue.size() == 0) {
                System.out.println("no processes in ready queue now.");
            }else{
                for (AGprocess process : readyQueue) {
                    System.out.println(process.getName());
                }
            }

            // loop for the current process.
            for (int i = 0; i < currProcess.getQuantm(); i++) {
                totalTime++; // to check arrival time for processes.
                System.out.println("total time: " + totalTime);

                // check if process finished its burst time before the quantum time ended.
                if (currProcess.getBurstTime() == (i + 1)) { // i indicates the time the process in cpu.

                    // add gui things here
                    ////////////////////////////////////////////////////////////////
                    // currProcess.setTurnaroundTime(currProcess.getProcessingTime() - currProcess.getArrivalTime());
                    currProcess.setTurnaroundTime(totalTime - currProcess.getArrivalTime());
                    currProcess.setWaitingTime(currProcess.getTurnaroundTime() - currProcess.getTempBurst());
               
                    int row = currProcess.getNumber();
                    
                    //Gui updating part
                    row = currProcess.getNumber(); 
                    this.schedulingGUI.updateTableRow(row, "Waiting Time", currProcess.getWaitingTime());
                    this.schedulingGUI.updateTableRow(row, "TAT", currProcess.getTurnaroundTime());
                    avrWaiting += currProcess.getWaitingTime();
                    avrTAT += currProcess.getTurnaroundTime();
                    ////////////////////////////////////////////////////////////////

                    System.out.println(currProcess.getName() + " its burst time ended.");
                    // update quantum time to 0
                    currProcess.setQuantm(0);
                    // add this process to die list
                    dieList.add(currProcess);

                    this.chart.AddColor(totalTime-i, currProcess.getNumber(), currProcess.getColor(), i+1);

                    // remove this process from processes list.
                    processesList.remove(processesList.indexOf(currProcess));
                    // get the first process in the ready queue.
                    currProcess = readyQueue.poll();
                    break;
                }

                // if the quantum time ends for this proocess.
                else if (i == currProcess.getQuantm() - 1) {
                    System.out.println(currProcess.getName() + " its quantum time ended.");
                    // update the quantum time.
                    int mean = calcMean(totalTime);
                    currProcess.setQuantm(currProcess.getQuantm() + mean);
                    System.out.println("new quantum time: " + currProcess.getQuantm());
                    // update burst time: decrease it by the amount consumed.
                    currProcess.setBurstTime(currProcess.getBurstTime() - (i + 1));
                    System.out.println("new burst time: " + currProcess.getBurstTime());
                    // // add current process to ready queue.
                    readyQueue.add(currProcess);
                    
                    this.chart.AddColor(totalTime-i, currProcess.getNumber(), currProcess.getColor(), i+1);

                    // get the first process in ready queue
                    currProcess = readyQueue.poll();
                    break;
                }

                // after 0.5 of its quantum time this process will be preemptive.
                else if (halfQTime <= i + 1) {
                    Boolean checkNewProcess = false;
                    // search for another procss with smaller ag factor to allocate cpu.
                    // this process should be with arrival time greater than total time.
                    AGprocess oldProcess = currProcess;
                    for (AGprocess process : processesList) {
                        // check for new process arrived with smaller ag factor. 
                        if (process.getAgFactor() < currProcess.getAgFactor() && process.getArrivalTime() <= totalTime && !readyQueue.contains(process)) {
                            currProcess = process;
                            checkNewProcess = true;
                        } 
                        // if the a new process arrived but with greater ag factor -> just push it in ready queue.
                        else if (process.getArrivalTime() <= totalTime && !readyQueue.contains(process) && process != oldProcess) {
                            System.out.println("new process arrived and added to ready queue! " + process.getName());
                            readyQueue.add(process);
                        }
                    }
                    if (checkNewProcess) {
                        System.out.println("new process arrived with smaller ag factor. " + currProcess.getName());
                        // update the quatum time: increase its Quantum time by the remaining unused Quantum time of this process.
                        oldProcess.setQuantm(oldProcess.getQuantm() + (oldProcess.getQuantm() - (i + 1))); // i refers to the quantum time consumend.
                        System.out.println("new quantum time " + oldProcess.getQuantm());
                        // update burst time: decrease it by the amount consumed.
                        oldProcess.setBurstTime(oldProcess.getBurstTime() - (i + 1));
                        System.out.println("new burst time: " + oldProcess.getBurstTime());

                        this.chart.AddColor(totalTime-i, oldProcess.getNumber(), oldProcess.getColor(), i+1);

                        // add current process to the ready queue
                        readyQueue.add(oldProcess);
                        break;
                    // if no new process arrived with smaller ag factor -> search in ready queue.
                    }else{
                        boolean found = false;
                        AGprocess oldProcessReady = currProcess;
                        AGprocess smallestProcess = currProcess;
                        for (AGprocess process: readyQueue) {
                            if(process.getAgFactor() < smallestProcess.getAgFactor() && process != currProcess){
                                found = true;
                                currProcess = process;
                            }
                        }
                        // if new process found with smaller ag factor in ready queue.
                        if(found){
                            readyQueue.remove(currProcess);
                            System.out.println("found process with smaller ag factor in ready queue. " + currProcess.getName());
                            // update the quatum time: increase its Quantum time by the remaining unused Quantum time of this process.
                            oldProcessReady.setQuantm(oldProcessReady.getQuantm() + (oldProcessReady.getQuantm() - (i + 1))); // i refers to the quantum time consumend.
                            System.out.println("new quantum time " + oldProcessReady.getQuantm());
                            // update burst time: decrease it by the amount consumed.
                            oldProcessReady.setBurstTime(oldProcessReady.getBurstTime() - (i + 1));
                            System.out.println("new burst time: " + oldProcessReady.getBurstTime());
                            
                            this.chart.AddColor(totalTime-i, oldProcessReady.getNumber(), oldProcessReady.getColor(), i+1);

                            // add old process to the ready queue
                            readyQueue.add(oldProcessReady);
                            break;
                        }
                    }
                }
            }
        }
        avrWaiting /= nProcess;
        avrWaiting = Math.floor(avrWaiting);
        avrTAT /= nProcess;
        avrTAT = Math.floor(avrTAT);
        Object[] avgWaiting = {"Average waiting time", avrWaiting};
        this.schedulingGUI.addRow(avgWaiting);
        System.out.println("Average waiting time: " + avrWaiting);
        Object[] avgTATRow = {"Average TAT time", avrTAT};
        this.schedulingGUI.addRow(avgTATRow);
        System.out.println("Average TAT: " + avrTAT);
    }

    public int calcMean(int totalTime) {
        int result = 0;
        int count = 0; // to count the number of precesses.
        for (AGprocess process : processesList) {
            // check if the current process is arrived.
            if (process.getArrivalTime() <= totalTime) {
                result += process.getQuantm();
                count++;
            }
        }

        return (int) Math.ceil(0.1 * (result / count));
    }

    public void makeAgFactor() {
        // for (AGprocess proc : processesList) {
        // int agFac = 0 , randNum;
        // randNum = (int) (Math.random() * 21); // creates number betweem 0->20
        // System.out.println("random number: " + randNum);

        // if (randNum < 10) {
        // agFac = randNum + proc.getArrivalTime() + proc.getBurstTime();
        // }else if (randNum > 10){
        // agFac = 10 + proc.getArrivalTime() + proc.getBurstTime();
        // }else if (randNum == 10 ){
        // agFac = proc.getPriority() + proc.getArrivalTime() + proc.getBurstTime();
        // }

        // proc.setAgFactor(agFac);
        // }
        processesList.get(0).setAgFactor(20);
        processesList.get(1).setAgFactor(17);
        processesList.get(2).setAgFactor(16);
        processesList.get(3).setAgFactor(43);
        // processesList.get(0).setAgFactor(17);
        // processesList.get(1).setAgFactor(12);
        // processesList.get(2).setAgFactor(24);
        // processesList.get(3).setAgFactor(43);
    }

    public void runProcess(AGprocess process, int compTime) {
        int bTime = process.getBurstTime();
        bTime--;
        process.setBurstTime(bTime);
        process.setProcessingTime(compTime);
    }

    public void printProcesses() {
        for (AGprocess process : processesList) {
            System.out.println("Process " + processesList.indexOf(process) + ":");
            System.out.println("name: " + process.getName());
            System.out.println("burst time: " + process.getBurstTime());
            System.out.println("arrival time" + process.getArrivalTime());
            System.out.println("priority: " + process.getPriority());
            System.out.println("quantum: " + process.getQuantm());
            System.out.println("ag factor: " + process.getAgFactor());
        }
    }

    public static void main(String[] args) {
        ArrayList<Process> Processes = new ArrayList<Process>();
        Processes.add(new Process("P1", "red", 0, 17, 4, 0));
        Processes.add(new Process("P2", "blue", 3, 6, 9, 1));
        Processes.add(new Process("P3", "yellow", 4, 10, 3, 2));
        Processes.add(new Process("P4", "black", 29, 4, 8, 3));

        // AG ag = new AG(Processes, 4);
        // ag.makeAgFactor();
        // ag.printProcesses();
        // System.out.println();
        // ag.startProcessing();
    }
}
