import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CPUScheduler {
    int noOfProcesses;
    int contextSwitch;
    int RRTimeQuantum;
    List<Process> processes;

    public CPUScheduler(int noOfProcesses, int contextSwitch, int RRTimeQuantum) {
        Scanner sc = new Scanner(System.in);
        this.noOfProcesses = noOfProcesses;
        this.contextSwitch = contextSwitch;
        this.RRTimeQuantum = RRTimeQuantum;
        processes = new ArrayList<>();
        for(int i = 0; i < noOfProcesses; i++){
            String name, color;
            int arrivalTime, burstTime, priorityNumber;
            System.out.println("Enter the process name: ");
            name = sc.nextLine();
            System.out.println("Enter the process color: ");
            color = sc.nextLine();
            System.out.println("Enter the process arrival time: ");
            arrivalTime = sc.nextInt();
            System.out.println("Enter the process burst time: ");
            burstTime = sc.nextInt();
            System.out.println("Enter the process priority number: ");
            priorityNumber = sc.nextInt();

            Process process = new Process(name, color, arrivalTime, burstTime, priorityNumber);
            processes.add(process);
        }

        int choice;
        System.out.println("Choose Scheduler: ");
        System.out.println("1- Shortest Job First.");
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                shortestJobFirst();
                break;
        
            default:
                break;
        }
    }

    public void shortestJobFirst(){
        System.out.println("hello");
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Eter the number of processes: ");
        int noOfProcesses = sc.nextInt();
        System.out.println("Eter the context switch: ");
        int contextSwitch = sc.nextInt();
        System.out.println("Eter the Round Robin Time Quantum: ");
        int RRTimeQuantum = sc.nextInt();

        CPUScheduler cpuScheduler = new CPUScheduler(noOfProcesses, contextSwitch, RRTimeQuantum);
    }
}
