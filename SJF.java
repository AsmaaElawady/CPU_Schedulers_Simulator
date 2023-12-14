import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF {
    ArrayList<Process> Processes;
    int contextSwitch;
    int noOfProcesses;
    ChartGUI chart; // to visualize the order of precesses in cpu.
    SchedulingGUI schedulingGUI; // to show the avg time and TAT for each process.

    public SJF(ArrayList<Process> Processes, int contextSwitch, ChartGUI chart, SchedulingGUI schedulingGUI){
        this.Processes = Processes;
        this.noOfProcesses = Processes.size();
        this.contextSwitch = contextSwitch;
        this.chart = chart;
        this.schedulingGUI = schedulingGUI;

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
                if(minProcess.getBurstTime() > process.getBurstTime() && process.getArrivalTime() <= totalTime){
                    minProcess = process;
                }
            }
            System.out.println("process " + minProcess.getName() + " is now in cpu.");

            // calculate waiting time for current process.
            int row = minProcess.getNumber();
            minProcess.setWaitingTime((totalTime - minProcess.getArrivalTime()) + this.contextSwitch); // waiting time = (start time - arrival time) + context switch.
            avgWaitingTime += minProcess.getWaitingTime();
            System.out.println("process " + minProcess.getName() + " waiting time: " + minProcess.getWaitingTime());
            this.schedulingGUI.updateTableRow(row, "Waiting Time", minProcess.getWaitingTime()); // Update waiting time in table
            // this.chart.AddColor(totalTime+1, minProcess.getNumber(), minProcess.getColor()); // add the process in the chart.
            this.chart.AddColor(totalTime+1, minProcess.getNumber(), minProcess.getColor(), minProcess.getBurstTime()); // add the process in the chart.
            
            totalTime += (minProcess.getBurstTime() + this.contextSwitch);
            
            // calculate TAT for current process.
            minProcess.setTurnaroundTime(minProcess.getWaitingTime() + minProcess.getBurstTime()); // TAT = waiting time + burst time.
            System.out.println("process " + minProcess.getName() + " TAT: " + minProcess.getTurnaroundTime());
            this.schedulingGUI.updateTableRow(row, "TAT", minProcess.getTurnaroundTime()); // Update TAT in table
            avgTAT += minProcess.getTurnaroundTime();
            this.Processes.remove(minProcess);
        }

        avgWaitingTime /= this.noOfProcesses;
        avgTAT /= this.noOfProcesses;
        Object[] avgWaiting = {"Average waiting time", avgWaitingTime};
        this.schedulingGUI.addRow(avgWaiting);
        System.out.println("Average waiting time: " + avgWaitingTime);
        Object[] avgTATRow = {"Average TAT time", avgTAT};
        this.schedulingGUI.addRow(avgTATRow);
        System.out.println("Average TAT: " + avgTAT);
    }

    public static void main(String[] args) {
        ArrayList<Process> Processes = new ArrayList<Process>();
        // Processes.add(new Process("P1", "red", 2, 6, 1, 0));
        // Processes.add(new Process("P2", "blue", 5, 2, 1, 1));
        // Processes.add(new Process("P3", "yellow", 1, 8, 1, 2));
        // Processes.add(new Process("P4", "black", 0, 3, 1, 3));
        // Processes.add(new Process("P5", "green", 4, 4, 1, 4));
        Processes.add(new Process("P1", "red", 0, 7, 1, 0));
        Processes.add(new Process("P2", "blue", 2, 4, 1, 1));
        Processes.add(new Process("P3", "yellow", 4, 1, 1, 2));
        Processes.add(new Process("P4", "black", 5, 4, 1, 3));
        new SJF(Processes, 1, null, null);
    }
}
