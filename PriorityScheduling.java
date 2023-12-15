// import java.awt.Color;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// public class PriorityScheduling {
//     ArrayList<Process> processes = new ArrayList<Process>(); //which processes have not yet been processed
//     ArrayList<Process> waitingQueue = new ArrayList<Process>(); //which of above queue has arrived and is waiting
//     ArrayList<Process> executedProcesses = new ArrayList<Process>(); //which have already been processed
//     ArrayList<Process> copy ;//which have already been processed

//     ChartGUI gui ;

//     int currentTime = 0 ;
//     int agingValue = 5 ;
//     PriorityScheduling(ArrayList<Process> P){
//         for(Process i : P)
//         {
//             processes.add(new Process(i));
//         }

//         gui = new ChartGUI(processes);
//         copy = new ArrayList<Process>(processes);

//         // sort the processes according to the arrival time.
//         Collections.sort(processes, Comparator.comparing(Process::getArrivalTime));

//         currentTime = processes.get(0).getArrivalTime();   // arrival time of the first process in the sorted processes list.
//         constructWaitingQueue(currentTime);

//     }
//     private void constructWaitingQueue(int currentTime) {
//         waitingQueue = new ArrayList<Process>();
//         for(int i = 0; i < processes.size() ; i++ ) {
//             // it means the process has arrived and is ready to be scheduled
//             if (processes.get(i).getArrivalTime() <= currentTime) {
//                 waitingQueue.add(processes.get(i));
//             } else
//                 break;
//         }
//     }

//     public void startScheduling() {
//         Process currentProcess = new Process();     // to keep track of the process that will be scheduled next.

//         // it updates the GUI to represent the scheduling state at the beginning. This includes setting the color for the current process in the Gantt chart.
//         gui.AddColor( 1 , copy.indexOf(currentProcess), new Color(255,255,255), currentTime - 1);

//         //  enter a loop that continues until all processes are processed.
//         while(processes.size() > 0 ) {
//             // check if no process  in the waiting queue
//             if(findMaxPriorityInWaiting()==null) {
//                 currentTime ++;
//                 gui.AddColor( currentTime ,copy.indexOf(currentProcess) , new Color(255,255,255));
//                 constructWaitingQueue(currentTime);
//             }
//             else {
//                // check if there are processes in the waiting queue and update the GUI and reconstruct the waiting queue.
//                 currentProcess = findMaxPriorityInWaiting();
//                 gui.AddColor(currentTime + 1 , copy.indexOf(currentProcess) , currentProcess.getColor(), currentProcess.getBurstTime());

//                 currentProcess.setStartTime(currentTime);
//                 currentTime += currentProcess.getBurstTime();

//                 // calculate waiting and turnaround time for the current process
//                 currentProcess.setWaitingTime( currentProcess.getStartTime() - currentProcess.getArrivalTime());
//                 currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime() );

//                 executedProcesses.add(currentProcess);
//                 processes.remove(currentProcess);

//                 constructWaitingQueue(currentTime);
//                 agingProcess(agingValue);
//                 currentProcess.execute();
//             }
//         }
//     }

//     public double getAverageWaiting() {
//         double sumOfWaiting = 0.0;
//         for(Process p : executedProcesses) {
//             // look each process wait kam and added and divide on number of process
//             sumOfWaiting+=p.getWaitingTime();
//         }
//         return sumOfWaiting / executedProcesses.size();
//     }
//     public double getAverageTurnAround() {
//         double sumOfTurnAround = 0.0;
//         for(Process p : executedProcesses) {
//             sumOfTurnAround+=p.getTurnaroundTime();
//         }
//         return sumOfTurnAround / executedProcesses.size();
//     }
//     private Process findMaxPriorityInWaiting() {
//         // keep track of the process with the highest priority
//         Process maxPriority = null;
//         if(waitingQueue.size()>0) {
//             maxPriority = waitingQueue.get(0);
//             // find the process with the maximum priority
//             for (int i = 1; i < waitingQueue.size(); i++) {
//                 if (maxPriority.getPriority() >= waitingQueue.get(i).getPriority()) {
//                     // if two processes have equal priority it chooses the one from them 3la 7sb arrivalTime
//                     if (maxPriority.getPriority() == waitingQueue.get(i).getPriority()) {
//                         if (maxPriority.getArrivalTime() > waitingQueue.get(i).getArrivalTime()) {
//                             maxPriority = waitingQueue.get(i);
//                         } else {
//                             // if process has less arrivalTime leave it as it is
//                             maxPriority = maxPriority;
//                         }
//                     } else {
//                         // If the priority of the current process is strictly greater, update maxPriority
//                         maxPriority = waitingQueue.get(i);
//                     }
//                 }
//             }
//         }
//         return maxPriority ;
//     }


//     private void agingProcess(int timeNeededToChange) {
//         Process p = new Process();
//         for(int i = 0 ; i < waitingQueue.size() ; i++ ) {
//             p = waitingQueue.get(i);
//             // check if the current process has a positive priority and is not the one with the maximum priority
//             if(p.getPriority() > 0 && p!= findMaxPriorityInWaiting()) {
//                 // calculate the number of priority increases based on the time elly 3da and timeNeededToChange
//                 int nIncreasesPrioroty = (currentTime - p.getLastTimeAged()) / timeNeededToChange;

//                 // ensure that nIncreasesInPriority is non negative
//                 if (nIncreasesPrioroty <= 0) {
//                     nIncreasesPrioroty = 0;
//                 }

//                 // decrease the priorit of the current process based on the calculated value
//                 p.setPriority(p.getPriority() - nIncreasesPrioroty);

//                 // update the last time the process was aged
//                 p.setLastTimeAged(currentTime + ((currentTime - p.getLastTimeAged()) % timeNeededToChange));
//             }
//         }
//     }

// }




import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class PriorityScheduling {
    ArrayList<Process> processes = new ArrayList<Process>(); //which processes have not yet been processed
    ArrayList<Process> waitingQueue = new ArrayList<Process>(); //which of above queue has arrived and is waiting
    ArrayList<Process> executedProcesses = new ArrayList<Process>(); //which have already been processed
    ArrayList<Process> copy ;//which have already been processed

    ChartGUI gui;
    SchedulingGUI schedulingGUI; // to show the avg time and TAT for each process.

    int currentTime = 0 ;
    int agingValue = 5 ;
    PriorityScheduling(ArrayList<Process> P , ChartGUI chart, SchedulingGUI schedulingGUI){
        // for(Process i : P)
        // {
        //     processes.add(new Process(i));
        // }
        this.processes = P;

        // gui = new ChartGUI(processes);
        this.gui = chart;
        copy = new ArrayList<Process>(processes);
        this.schedulingGUI = schedulingGUI;


        // sort the processes according to the arrival time.
        Collections.sort(processes, Comparator.comparing(Process::getArrivalTime));

        currentTime = processes.get(0).getArrivalTime();   // arrival time of the first process in the sorted processes list.
        constructWaitingQueue(currentTime);

    }
    
    private void constructWaitingQueue(int currentTime) {
        waitingQueue = new ArrayList<Process>();
        for(int i = 0; i < processes.size() ; i++ ) {
            // it means the process has arrived and is ready to be scheduled
            if (processes.get(i).getArrivalTime() <= currentTime) {
                waitingQueue.add(processes.get(i));
            } else
                break;
        }
    }

    public void startScheduling() {
        Process currentProcess = new Process();     // to keep track of the process that will be scheduled next.

        // it updates the GUI to represent the scheduling state at the beginning. This includes setting the color for the current process in the Gantt chart.
        gui.AddColor( 1 , copy.indexOf(currentProcess), new Color(255,255,255), currentTime - 1);

        //  enter a loop that continues until all processes are processed.
        while(processes.size() > 0 ) {
            // check if no process  in the waiting queue
            if(findMaxPriorityInWaiting()==null) {
                currentTime ++;
                gui.AddColor( currentTime ,copy.indexOf(currentProcess) , new Color(255,255,255));
                constructWaitingQueue(currentTime);
            }
            else {
               // check if there are processes in the waiting queue and update the GUI and reconstruct the waiting queue.
                currentProcess = findMaxPriorityInWaiting();
                gui.AddColor(currentTime + 1 , copy.indexOf(currentProcess) , currentProcess.getColor(), currentProcess.getBurstTime());

                currentProcess.setStartTime(currentTime);
                currentTime += currentProcess.getBurstTime();

                // calculate waiting and turnaround time for the current process
                currentProcess.setWaitingTime( currentProcess.getStartTime() - currentProcess.getArrivalTime());
                this.schedulingGUI.updateTableRow(currentProcess.getNumber(), "Waiting Time", currentProcess.getWaitingTime()); // Update waiting time in table
                currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime() );
                this.schedulingGUI.updateTableRow(currentProcess.getNumber(), "TAT", currentProcess.getTurnaroundTime()); // Update TAT in table

                executedProcesses.add(currentProcess);
                processes.remove(currentProcess);

                constructWaitingQueue(currentTime);
                agingProcess(agingValue);
                currentProcess.execute();
                Object[] avgWaiting = {"Average waiting time", getAverageWaiting()};
                this.schedulingGUI.addRow(avgWaiting);
                Object[] avgTATRow = {"Average TAT time", getAverageTurnAround()};
                this.schedulingGUI.addRow(avgTATRow);
            }
        }
    }

    public double getAverageWaiting() {
        double sumOfWaiting = 0.0;
        for(Process p : executedProcesses) {
            // look each process wait kam and added and divide on number of process
            sumOfWaiting+=p.getWaitingTime();
        }
        return sumOfWaiting / executedProcesses.size();
    }
    public double getAverageTurnAround() {
        double sumOfTurnAround = 0.0;
        for(Process p : executedProcesses) {
            sumOfTurnAround+=p.getTurnaroundTime();
        }
        return sumOfTurnAround / executedProcesses.size();
    }
    private Process findMaxPriorityInWaiting() {
        // keep track of the process with the highest priority
        Process maxPriority = null;
        if(waitingQueue.size()>0) {
            maxPriority = waitingQueue.get(0);
            // find the process with the maximum priority
            for (int i = 1; i < waitingQueue.size(); i++) {
                if (maxPriority.getPriority() >= waitingQueue.get(i).getPriority()) {
                    // if two processes have equal priority it chooses the one from them 3la 7sb arrivalTime
                    if (maxPriority.getPriority() == waitingQueue.get(i).getPriority()) {
                        if (maxPriority.getArrivalTime() > waitingQueue.get(i).getArrivalTime()) {
                            maxPriority = waitingQueue.get(i);
                        } else {
                            // if process has less arrivalTime leave it as it is
                            maxPriority = maxPriority;
                        }
                    } else {
                        // If the priority of the current process is strictly greater, update maxPriority
                        maxPriority = waitingQueue.get(i);
                    }
                }
            }
        }
        return maxPriority ;
    }


    private void agingProcess(int timeNeededToChange) {
        Process p = new Process();
        for(int i = 0 ; i < waitingQueue.size() ; i++ ) {
            p = waitingQueue.get(i);
            // check if the current process has a positive priority and is not the one with the maximum priority
            if(p.getPriority() > 0 && p!= findMaxPriorityInWaiting()) {
                // calculate the number of priority increases based on the time elly 3da and timeNeededToChange
                int nIncreasesPrioroty = (currentTime - p.getLastTimeAged()) / timeNeededToChange;

                // ensure that nIncreasesInPriority is non negative
                if (nIncreasesPrioroty <= 0) {
                    nIncreasesPrioroty = 0;
                }

                // decrease the priorit of the current process based on the calculated value
                p.setPriority(p.getPriority() - nIncreasesPrioroty);

                // update the last time the process was aged
                p.setLastTimeAged(currentTime + ((currentTime - p.getLastTimeAged()) % timeNeededToChange));
            }
        }
    }

}