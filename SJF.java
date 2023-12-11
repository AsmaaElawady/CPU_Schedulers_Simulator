import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SJF {
    ArrayList<Process> Processes;
    int contextSwitch;
    int noOfProcesses;
    SchedulingGUI gui;

    public SJF(ArrayList<Process> Processes, int contextSwitch){
        this.Processes = Processes;
        this.noOfProcesses = Processes.size();
        this.contextSwitch = contextSwitch;
        gui = new SchedulingGUI(Processes);

        // sort the processes according to the arrival time.
        Collections.sort(Processes, Comparator.comparing(Process::getArrivalTime));
        int totalTime = 0; // to save the total time consumend in cpu.
        double avgWaitingTime = 0;
        double avgTAT = 0;
        while(Processes.size() > 0){
            Process minProcess = Processes.get(0);

            // search for the min process in the current time.
            // the process will work in cpu now of it: 1- has burst time less than others. 2- its arrival time is lower than the currTime (to make sure it is arrived).
            for (Process process : Processes) {
                // System.out.println("process: " + process.getName());
                if(minProcess.getBurstTime() > process.getBurstTime() && process.getArrivalTime() <= totalTime){
                    // System.out.println("min process: " + process.getName());
                    minProcess = process;
                }
            }

            System.out.println("process " + minProcess.getName() + " is now in cpu.");
            // first process, this exception because i will not add the context switch in the avg time.
            if(totalTime == 0){
                avgWaitingTime += totalTime - minProcess.getArrivalTime();
                System.out.println("process " + minProcess.getName() + " waiting time: " + (totalTime - minProcess.getArrivalTime()));
                gui.AddColor(totalTime+1, minProcess.getNumber(), minProcess.getColor());
                totalTime += minProcess.getBurstTime();
                System.out.println("process " + minProcess.getName() + " TAT: " + (totalTime - minProcess.getArrivalTime())); // to calculate TAT: the time the process finished minus the arrival time.
                avgTAT += totalTime - minProcess.getArrivalTime();
            }else{
                avgWaitingTime += (totalTime - minProcess.getArrivalTime()) + this.contextSwitch;
                System.out.println("process " + minProcess.getName() + " waiting time: " + ((totalTime - minProcess.getArrivalTime()) + this.contextSwitch));
                gui.AddColor(totalTime+1, minProcess.getNumber(), minProcess.getColor());
                totalTime += (minProcess.getBurstTime() + this.contextSwitch);
                System.out.println("process " + minProcess.getName() + " TAT: " + (totalTime - minProcess.getArrivalTime())); // to calculate TAT: the time the process finished minus the arrival time.
                avgTAT += totalTime - minProcess.getArrivalTime();
                // هل بجمع ال contaxt switch برضو ف ال TAT?
            }
            Processes.remove(minProcess);
        }

        avgWaitingTime /= this.noOfProcesses;
        avgTAT /= this.noOfProcesses;
        System.out.println("Average waiting time: " + avgWaitingTime);
        System.out.println("Average waiting time: " + avgTAT);
    }

    public static void main(String[] args) {
        ArrayList<Process> Processes = new ArrayList<Process>();
        Processes.add(new Process("P1", "red", 2, 6, 1, 0));
        Processes.add(new Process("P2", "blue", 5, 2, 1, 1));
        Processes.add(new Process("P3", "yellow", 1, 8, 1, 2));
        Processes.add(new Process("P4", "black", 0, 3, 1, 3));
        Processes.add(new Process("P5", "green", 4, 4, 1, 4));
        // Processes.add(new Process("P1", "red", 0, 7, 1, 0));
        // Processes.add(new Process("P2", "blue", 2, 4, 1, 1));
        // Processes.add(new Process("P3", "yellow", 4, 1, 1, 2));
        // Processes.add(new Process("P4", "black", 5, 4, 1, 3));
        new SJF(Processes, 5);
    }
}
