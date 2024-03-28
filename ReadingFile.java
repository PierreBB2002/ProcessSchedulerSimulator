import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadingFile {
	 public static ArrayList<Process> readInputFile(String fileName) throws FileNotFoundException{
		 ArrayList<Process> processes = new ArrayList<>();
		 BufferedReader reader = new BufferedReader(new FileReader(fileName));
		 String line;
		 int[] ioBursts = null;
		 int ind=0;
		 try {
			while ((line = reader.readLine()) != null) {
			     String[] parts = line.split(",");
			     int pid = ind;
			     int arrivalTime = Integer.parseInt(parts[1]);
			     int numCPUBursts = Math.abs((parts.length - 2) / 2);
			     if(parts.length % 2 != 0) {
			    	 numCPUBursts ++;
			     }
			
			     int[] cpuBursts = new int[numCPUBursts];
			     ArrayList<Integer> cpuBurstsList = new ArrayList<>(cpuBursts.length);
			     if(numCPUBursts>1) {
			    	 ioBursts = new int[numCPUBursts - 1];
			     }
			     else {
			    	 ioBursts = new int[0];
			     }
			     for (int i = 0; i < numCPUBursts; i++) {
			         cpuBursts[i] = Integer.parseInt(parts[i * 2 + 2]);
			         if (i < numCPUBursts - 1) {
			             ioBursts[i] = Integer.parseInt(parts[i * 2 + 3]);
			         }
			     }
			     for (int i = 0; i < cpuBursts.length; i++) {
			         cpuBurstsList.add(cpuBursts[i]);
			     }
			    Process process = new Process(pid, arrivalTime, cpuBurstsList , ioBursts);
			    processes.add(process);
			     ind++;
			 }
			reader.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return processes;
		 
	 }
}

