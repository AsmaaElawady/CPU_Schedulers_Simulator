import java.util.ArrayList;
import java.util.Scanner;

public class CPUScheduler {

    static public void main(String[] arg) {
        int noOfProcesses;
        int contextSwitch;
        int RRTimeQuantum;
        ArrayList<Process> Processes = new ArrayList<Process>();

        Scanner sc = new Scanner(System.in);

        System.out.println("the number of processes: ");
        noOfProcesses = sc.nextInt();

        System.out.println("Enter the context switch: ");
        contextSwitch = sc.nextInt();

        System.out.println("the Round Robin Time Quantum: ");
        RRTimeQuantum = sc.nextInt();

        // Read Processes
        for (int i = 0; i < noOfProcesses; i++) {
            sc = new Scanner(System.in);
            Process p = new Process();
            System.out.println((i + 1) + "Enter the process name: ");
            p.setName(sc.nextLine());

            System.out.println("Enter the process color: ");
            p.setColor(sc.nextLine());

            System.out.println("Enter the process arrival time: ");
            p.setArrivalTime(sc.nextInt());

            System.out.println("Enter the process burst time: ");
            p.setBurstTime(sc.nextInt());

            System.out.println("Enter the process priority number: ");
            p.setPriority(sc.nextInt());

            Processes.add(p);
        }

        System.out.println("\nSelect the Scheduler you want to use : "
                + "\n1-Non-Preemptive Shortest- Job First (SJF)"
                + "\n2-Shortest-Remaining Time First (SRTF) Scheduling"
                + "\n3-Non-preemptive Priority Scheduling."
                + "\n4-AG Scheduling \n5-End");
        int select = sc.nextInt();
        if (select == 1) {
            SJF sjf = new SJF(Processes, contextSwitch);
        } else if (select == 2) {
        }
        // else if(select == 3)
        // {
        // PriorityScheduling pScheduling = new PriorityScheduling(Processes);
        // pScheduling.startScheduling();

        // System.out.println( "average Waiting Time : " +
        // pScheduling.getAverageWaiting());
        // System.out.println("average Turnaround Time :" +
        // pScheduling.getAverageTurnAround() + "\n");
        // }
        else if (select == 4) {
        } else {
            System.out.println("Invalid input");
        }

        // for(int i = 0 ; i < Processes.size();i++)
        // Processes.get(i).printProcess();
    }

}
