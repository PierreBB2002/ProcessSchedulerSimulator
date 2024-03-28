import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Collections;


public class WorkloadGenerator {
	
	public static void main(String[] args) throws InterruptedException {
        System.out.print("Enter the name of the file: ");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        System.out.println("How do you want to read the file?");
        System.out.println("1. Random File");
        System.out.println("2. Case file");
        int choice = in.nextInt();
        int numProcesses = 0;
		int maxArrivalTime = 0;
		int maxCPUBursts = 0;
		int minIO = 0;
		int maxIO = 0;
		int minCPU = 0;
		int maxCPU = 0;
		if(choice == 1) {
        	 System.out.print("Enter the number of processes: ");
             numProcesses = in.nextInt();
             System.out.print("Enter the maximum arrival time: ");
             maxArrivalTime = in.nextInt();
             System.out.print("Enter the maximum CPU bursts: ");
             maxCPUBursts = in.nextInt();
             System.out.println("Enter the range of the I/O");
             System.out.print("Min: ");
             minIO = in.nextInt();
             System.out.print("Max: ");
             maxIO = in.nextInt();
             System.out.println("Enter the range of the CPU");
             System.out.print("Min: ");
             minCPU = in.nextInt();
             System.out.print("Max: ");
             maxCPU = in.nextInt();
             Random rand = new Random();
             try {
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

                 for (int i = 0; i < numProcesses; i++) {
                     int arrivalTime = rand.nextInt(maxArrivalTime);
                     int numCPUBursts = rand.nextInt(maxCPUBursts) + 2;

                     writer.write("P" + i + "," + arrivalTime);

                     for (int j = 0; j < numCPUBursts; j++) {
                         int cpuBurst = rand.nextInt(maxCPU - minCPU) + minCPU;
                         writer.write("," + cpuBurst);
                         
                         if (j < numCPUBursts - 1) {
                             int ioBurst = rand.nextInt(maxIO - minIO) + minIO;
                             writer.write("," + ioBurst);
                         }
                     }

                     writer.newLine();
                 }

                 writer.close();
                 
             } catch (IOException e) {
                 e.printStackTrace();
             }
        }
        boolean isPaused = false;
        ArrayList <Process> queue1 = new ArrayList<>();
        ArrayList <Process> queue2 = new ArrayList<>();
        ArrayList <Process> queue3 = new ArrayList<>();
        ArrayList <Process> queue4 = new ArrayList<>();
        ArrayList <Process> Pqueue1 = new ArrayList<>();
        ArrayList <Process> Pqueue2 = new ArrayList<>();
        ArrayList <Process> Pqueue3 = new ArrayList<>();
        ArrayList <Process> Pqueue4 = new ArrayList<>();
        ArrayList <Process> queue3Fin = new ArrayList<>();
        ArrayList <Process> IO = new ArrayList<>();
        ArrayList <Process> completed = new ArrayList<>();
        ArrayList <Process> gant = new ArrayList<>();
        int clock1 = 0;
        System.out.print("Enter q1: "); 
        int q1 = 10*in.nextInt();
        System.out.print("Enter q2: ");
        int q2 = 10*in.nextInt();
        System.out.print("Enter Alpha: ");
        double alpha = in.nextDouble();
        try {
			while(true) {
				for (int i = 0; i < 2; i++)
				{
				    Thread.sleep(1000); //Delays 1s 10 times; 10s overall delay
				}
				String input = in.nextLine();
			      if (input.equals("pause")) {
			        isPaused = true;
			        System.out.println("Simulation paused. Type 'resume' to continue.");
			        System.out.println("Queue 1:");
			        for(int i=0; i<Pqueue1.size(); i++) {
			        	System.out.println(Pqueue1.get(i).toString());
			        }
			        System.out.println("Queue 2:");
			        for(int i=0; i<Pqueue2.size(); i++) {
			        	System.out.println(Pqueue2.get(i).toString());
			        }
			        System.out.println("Queue 3:");
			        for(int i=0; i<Pqueue3.size(); i++) {
			        	System.out.println(Pqueue3.get(i).toString());
			        }
			        System.out.println("Queue 4:");
			        for(int i=0; i<Pqueue4.size(); i++) {
			        	System.out.println(Pqueue4.get(i).toString());
			        }
			      } else if (input.equals("resume")) {
			        isPaused = false;
			        System.out.println("Simulation resumed.");
			      } else if (input.equals("status")) {
			        System.out.println("Current status of processes:");
			      }
			      
			      if (!isPaused) {
			        //Code here
			    	  ArrayList<Process> processes = ReadingFile.readInputFile(filename);
			    	  int sumIO = 0;
			    	  int sumCPU = 0;
			    	  for(int i=0; i<processes.size(); i++){
			    		  int io[] = processes.get(i).getIoBursts();
			    		  for(int j=0; j<io.length; j++) {
			    			  sumIO += io[j];
			    		  }
			    		  for(int j=0; j<processes.get(i).getCpuBursts().size(); j++) {
			    			  sumCPU += processes.get(i).getCpuBursts().get(j);
			    		  }
			    	  }
			    	  double CPUut = 0;
						if(sumCPU != 0) {
							CPUut = ((double)sumCPU / ((double)sumCPU + (double)sumIO)) * 100;
							Math.round(CPUut);
						}
			    	  System.out.println();
			    	  System.out.println("Processes:");
			    	  for(int i=0; i<processes.size(); i++) {
			    		  System.out.println(processes.get(i).toString());
			    	  }
			    	  System.out.println();
						int sumValue = 0;
						for(int i=0; i<processes.size(); i++) {
							for(int j=0; j<processes.get(i).getCpuBursts().size(); j++) {
								sumValue += processes.get(i).getCpuBursts().get(j);
							}
						}
						Collections.sort(processes, new Comparator<Process>() {
						    public int compare(Process p1, Process p2) {
						        return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
						    }
						});
						int timer = processes.get(0).getArrivalTime();
						int clock = 0;
						for(int i=0; i<processes.size(); i++) {
							try {
								queue1.add(processes.get(i));
								}catch(NullPointerException e) {
									e.printStackTrace();
								}
						}
						while ((!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty() || !queue4.isEmpty() )&& !processes.isEmpty()) {
							int ind = 0;
							while (!queue1.isEmpty()) {
								Process currentProc;
								if(queue1.get(ind).getArrivalTime() <= clock) {
									currentProc = queue1.remove(ind);
								}
								else {
									break;
								}
								Pqueue1.add(currentProc);
								currentProc.addStart(clock);
								for(int i=0; i<queue1.size(); i++) {
									if(!completed.contains(queue1.get(i))) {
										queue1.get(i).setWaiting(queue1.get(i).getWaiting()+ Math.min(q1, currentProc.getRemainingTime()));
									}
								}
								if(currentProc.getRemainingTime() > q1 && currentProc.getArrivalTime() <= clock) {
									currentProc.setRemainingTime(currentProc.getRemainingTime() - q1);
									currentProc.setRunning(currentProc.getRunning() + q1);
									queue2.add(currentProc);
									currentProc.setState("running");
									clock += q1;
								}
								else if (currentProc.getRemainingTime() <= q1 &&currentProc.getArrivalTime() <= clock){
									currentProc.setRunning(currentProc.getRunning() + currentProc.getRemainingTime());
									clock += currentProc.getRemainingTime();
									currentProc.setRemainingTime(0);
									currentProc.setState("ready");
									currentProc.addStart(clock);
									IO.add(currentProc);
									if(!currentProc.getCpuBursts().isEmpty()) {
										currentProc.getCpuBursts().remove(ind);
										if(!currentProc.getCpuBursts().isEmpty()) {
											currentProc.setRemainingTime(currentProc.getCpuBursts().get(ind));
										}
										else {
											completed.add(currentProc);
											processes.remove(currentProc);
										}
									}
									currentProc.setRunning(currentProc.getRunning()+currentProc.getRemainingTime());
									currentProc.setQueueNo(1);
								}
								currentProc.addEnd(clock);
								gant.add(currentProc);

							}
							while (!queue2.isEmpty()) {
								Process currentProc = queue2.remove(ind);
								Pqueue2.add(currentProc);
								currentProc.addStart(clock);
								if(!queue1.isEmpty()) {
									Process p1 = queue1.get(0);
									if(p1.getArrivalTime() <= clock) {
										queue1.remove(0);
										p1.addStart(clock);
										p1.setRemainingTime(p1.getRemainingTime() - q1);
										clock += q1;
										p1.addEnd(clock);
										gant.add(p1);
										queue2.add(p1);
									}
								}
								for(int i=0; i<queue2.size(); i++) {
									if(!completed.contains(queue2.get(i))) {
										queue2.get(i).setWaiting(queue2.get(i).getWaiting()+ Math.min(q2, currentProc.getRemainingTime()));
									}
								}
								if(currentProc.getRemainingTime() > q2 && currentProc.getArrivalTime() <= clock) {
									currentProc.setPrevCPUBurst(currentProc.getRemainingTime());
									currentProc.setRemainingTime(currentProc.getRemainingTime() - q2);
									currentProc.setRunning(currentProc.getRunning() + q2);
									queue3.add(currentProc);
									currentProc.setState("running");
									clock += q2;
								}
								else if(currentProc.getArrivalTime() <= clock){
									clock += currentProc.getRemainingTime();
									currentProc.setRemainingTime(0);
									currentProc.setState("ready");
									IO.add(currentProc);
									currentProc.addStart(clock);
									if(!currentProc.getCpuBursts().isEmpty()) {
										currentProc.getCpuBursts().remove(ind);
										if(!currentProc.getCpuBursts().isEmpty()) {
											currentProc.setRemainingTime(currentProc.getCpuBursts().get(ind));
										}
										else {
											completed.add(currentProc);
											processes.remove(currentProc);
										}
									}
									currentProc.setRunning(currentProc.getRunning()+currentProc.getRemainingTime());
									for(int i=0; i<queue2.size(); i++) {
										queue2.get(i).setWaiting(queue2.get(i).getWaiting()+ currentProc.getRemainingTime());
									}
									currentProc.setQueueNo(2);
								}
								currentProc.addEnd(clock);
								gant.add(currentProc);
							}
							while (!queue3.isEmpty()) {
								Process currentProc = queue3.remove(ind);
								int prevCPU = currentProc.getPrevCPUBurst();
								int currCPU = currentProc.getRemainingTime();
								int estBT = (int) (alpha*currCPU + ((1 - alpha)*prevCPU));
								currentProc.setEst(estBT);
								currentProc.setRemainingTime(currentProc.getEst());
								queue3Fin.add(currentProc);
							}
							while(!queue3Fin.isEmpty()) {
								Collections.sort(queue3Fin, (p1, p2) -> p1.getArrivalTime() - p2.getArrivalTime());
								Process currProc = queue3Fin.get(ind);
								Process currProc1 = queue3Fin.get(ind);
								currProc1.addStart(clock);
								int isFin = -1;
								if(currProc.getRemainingTime() <= 0) {
									isFin = 1;
									queue3Fin.remove(currProc);
									IO.add(currProc);
									currProc.addStart(clock);
									if(!currProc.getCpuBursts().isEmpty()) {
										currProc.getCpuBursts().remove(ind);
										if(!currProc.getCpuBursts().isEmpty()) {
											currProc.setRemainingTime(currProc.getCpuBursts().get(ind));
										}
										else {
											completed.add(currProc);
											processes.remove(currProc);
										}
									}
									currProc.setQueueNo(3);
								}
								else {
									Collections.sort(queue3Fin, (p1, p2) -> p1.getRemainingTime() - p2.getRemainingTime());
									if((currProc.getRemainingTime() > queue3Fin.get(ind).getRemainingTime()) && (timer >= queue3Fin.get(ind).getArrivalTime())) {
										currProc = queue3Fin.get(ind);
										Pqueue4.add(currProc);
										if(currProc1 != currProc) {
											currProc.addStart(clock);
											currProc1.addEnd(clock);
											gant.add(currProc1);
										}
										currProc.setPreemptionCount("adding");
										if(currProc.getPreemptionCount() == 3) {
											queue3Fin.remove(currProc);
											queue4.add(currProc);
										}
									}
									else if(!queue2.isEmpty()) {
										Process p1 = queue2.get(0);
										if(p1.getArrivalTime() <= clock) {
											queue2.remove(0);
											p1.addStart(clock);
											p1.setRemainingTime(p1.getRemainingTime() - q2);
											clock += q2;
											p1.addEnd(clock);
											gant.add(p1);
											queue3Fin.add(p1);
										}
										
									}
									currProc.setRemainingTime(currProc.getRemainingTime()-1);
									for(int j=0; j<queue3Fin.size(); j++) {
										if(queue3Fin.get(j) != currProc && !completed.contains(queue3Fin.get(j))) {
											queue3Fin.get(j).setWaiting(queue3Fin.get(j).getWaiting() + 1);
										}
									}
									timer++;
									clock++;
								}
								if(!processes.isEmpty()) {
									timer = processes.get(0).getArrivalTime();
								}
								else {
									break;
								}
								if(isFin == 1) {
									currProc1.addEnd(clock);
									Pqueue3.add(currProc);
									gant.add(currProc1);
								}
							}
							for(int i=0; i<queue4.size(); i++) {
								IO.add(queue4.get(i));
								queue4.get(i).addStart(clock);
								clock += queue4.get(i).getRemainingTime();
								queue4.get(i).addEnd(clock);
								gant.add(queue4.get(i));
								if(!queue4.get(i).getCpuBursts().isEmpty() && queue4.get(i).getCpuBursts().size() > 1) {
									queue4.get(i).getCpuBursts().remove(ind);
									if(!queue4.get(i).getCpuBursts().isEmpty()) {
										queue4.get(i).getCpuBursts().remove(ind);
										if(!queue4.get(i).getCpuBursts().isEmpty()) {
											queue4.get(i).setRemainingTime(queue4.get(i).getCpuBursts().get(ind));
										}
										else {
											completed.add(queue4.get(i));
											processes.remove(queue4.get(i));
										}
									}
									queue4.get(i).setRemainingTime(queue4.get(i).getCpuBursts().get(ind));
								}
								Process currProc = queue4.remove(0);
								for(int j=0; j<queue4.size(); j++) {
									queue4.get(j).setWaiting(queue4.get(j).getWaiting() + currProc.getRemainingTime());
								}
								queue4.get(i).setQueueNo(4);
							}
							while(!IO.isEmpty()) {
								clock1 = clock;
								int ind1 = 0;
								Process currProcIO = IO.get(ind1);
								clock1 += currProcIO.getCurrentIOBurstTime();
								if(currProcIO.getCpuBursts().isEmpty()) {
									break;
								}
								int pos = 0;
								while(currProcIO.getCurrentIOBurstTime() == 0) {
									if(pos < currProcIO.getIoBursts().length) {
										currProcIO.setCurrentIOBurstTime(currProcIO.getIOBursts(pos));
									}
									if(pos >currProcIO.getIoBursts().length) {
										int qn = currProcIO.getQueueNo();
										if(qn == 1) {
											queue1.add(currProcIO);
										}
										if(qn == 2) {
											queue2.add(currProcIO);
										}
										if(qn == 3) {
											queue3.add(currProcIO);
										}
										if(qn == 4) {
											queue4.add(currProcIO);
										}
										IO.remove(ind1);
									}
									pos++;
								}
								while(currProcIO.getCurrentIOBurstTime() != 0) {
									currProcIO.setCurrentIOBurstTime(currProcIO.getCurrentIOBurstTime()-1);
									if(currProcIO.getCurrentIOBurstTime() == 0) {
										int qn=currProcIO.getQueueNo();
										if(qn == 1) {
											queue1.add(currProcIO);
										}
										if(qn == 2) {
											queue2.add(currProcIO);
										}
										if(qn == 3) {
											queue3.add(currProcIO);
										}
										if(qn == 4) {
											queue4.add(currProcIO);
										}
										IO.remove(ind1);
									}
								}
							}
						}
						System.out.println();
						System.out.println("Gant Chart: \n");
						System.out.println("--------------------------------------------------------------------------------------------------");
						for(int i=0; i<gant.size(); i++) {
							if(!gant.get(i).getStart().isEmpty() && !gant.get(i).getEnd().isEmpty()) {
								System.out.print("P" + gant.get(i).getPid()+": " + "["+gant.get(i).getStart().get(0) +" : "+ gant.get(i).getEnd().get(0) + "]" + "|"+ "\t");
								gant.get(i).getStart().remove(0);
								gant.get(i).getEnd().remove(0);
								
							}
						}
						System.out.println();
						System.out.println("--------------------------------------------------------------------------------------------------");
						System.out.println();
						int sum = 0;
						for(int i=0 ; i<completed.size(); i++) {
							int finAvg = Math.max(0, completed.get(i).getWaiting() - completed.get(i).getArrivalTime());
							sum += finAvg;
						}
						double AvgWaiting = (double)sum / (double)completed.size();
						System.out.println("Average waiting: "+ AvgWaiting);
						System.out.printf("CPU utilization : %.2f %%\n", CPUut);
			      }
			}
		}
        catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
