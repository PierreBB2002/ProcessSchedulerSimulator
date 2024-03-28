import java.util.ArrayList;
import java.util.Arrays;

public class Process {
	 	private int pid;
	    private int arrivalTime;
	    private ArrayList<Integer> cpuBursts;
	    private int[] ioBursts;
	    private int currentBurst;
	    private int currentBurstTime;
	    private int currentIOBurst;
	    private int currentIOBurstTime;
	    private String state;
	    private int currentQueue;
	    private int waitingTime;
	    private int preemptionCount;
	    private int remainingTime;
	    private int prevCPU;
	    private int est_time;
	    private int queueNo;
	    private int waiting;
	    private int running;
	    private ArrayList <Integer> start;
	    private ArrayList <Integer>  end;
		public Process(int pid, int arrivalTime, ArrayList<Integer> cpuBursts, int[] ioBursts, int currentBurst,
				int currentBurstTime, int currentIOBurst, int currentIOBurstTime, String state, int currentQueue,
				int waitingTime, int preemptionCount, int remainingTime) {
			super();
			this.pid = pid;
			this.arrivalTime = arrivalTime;
			this.cpuBursts = cpuBursts;
			this.ioBursts = ioBursts;
			this.currentBurst = currentBurst;
			this.currentBurstTime = currentBurstTime;
			this.currentIOBurst = currentIOBurst;
			this.currentIOBurstTime = currentIOBurstTime;
			this.state = state;
			this.currentQueue = currentQueue;
			this.waitingTime = waitingTime;
			this.preemptionCount = preemptionCount;
			this.remainingTime = remainingTime;
		}
		
		public Process(int pid, int arrivalTime, ArrayList<Integer> cpuBursts, int[] ioBursts) {
			super();
			this.pid = pid;
	        this.arrivalTime = arrivalTime;
	        this.cpuBursts = cpuBursts;
	        this.ioBursts = ioBursts;
	        this.currentBurst = 0;
	        this.currentIOBurst = 0;
	        this.state = "waiting";
	        this.currentQueue = 1;
	        this.waitingTime = 0;
	        this.preemptionCount = 0;
	        this.remainingTime = cpuBursts.get(0);
	        if(ioBursts.length > 0) {
	        	this.currentIOBurstTime = ioBursts[0];
	        }
	        this.prevCPU = 0;
	        this.est_time = 0;
	        this.running = 0;
	        this.waiting = 0;
	        this.start = new ArrayList <>();
	        this.end = new ArrayList <>();
		}

		public int getPid() {
			return pid;
		}
		public void setPid(int pid) {
			this.pid = pid;
		}
		public int getArrivalTime() {
			return arrivalTime;
		}
		public void setArrivalTime(int arrivalTime) {
			this.arrivalTime = arrivalTime;
		}
		public ArrayList<Integer> getCpuBursts() {
			return cpuBursts;
		}
		public void setCpuBursts(ArrayList<Integer> cpuBursts) {
			this.cpuBursts = cpuBursts;
		}
		public int[] getIoBursts() {
			return ioBursts;
		}
		public void setIoBursts(int[] ioBursts) {
			this.ioBursts = ioBursts;
		}
		public int getCurrentBurst() {
			return currentBurst;
		}
		public void setCurrentBurst(int currentBurst) {
			this.currentBurst = currentBurst;
		}
		public int getCurrentBurstTime() {
			return currentBurstTime;
		}
		public void setCurrentBurstTime(int currentBurstTime) {
			this.currentBurstTime = currentBurstTime;
		}
		public int getCurrentIOBurst() {
			return currentIOBurst;
		}
		public void setCurrentIOBurst(int currentIOBurst) {
			this.currentIOBurst = currentIOBurst;
		}
		public int getCurrentIOBurstTime() {
			return currentIOBurstTime;
		}
		public void setCurrentIOBurstTime(int currentIOBurstTime) {
			this.currentIOBurstTime = currentIOBurstTime;
		}
		public int getIOBursts(int pos) {
			return ioBursts[pos];
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public int getCurrentQueue() {
			return currentQueue;
		}
		public void setCurrentQueue(int currentQueue) {
			this.currentQueue = currentQueue;
		}
		public int getWaitingTime() {
			return waitingTime;
		}
		public void setWaitingTime(int waitingTime) {
			this.waitingTime = waitingTime;
		}
		public int getPreemptionCount() {
			return preemptionCount;
		}
		public void setPreemptionCount(String adding) {
			this.preemptionCount = preemptionCount+1;
		}
		public int getRemainingTime() {
			return remainingTime;
		}
		public void setRemainingTime(int d) {
			this.remainingTime = d;
		}
		public void setPrevCPUBurst(int remainingTime) {
			this.prevCPU = remainingTime;
		}
		public int getPrevCPUBurst() {
			return prevCPU;
		}
		public void setEst(int est) {
			this.est_time = est;
		}
		public int getEst() {
			return est_time;
		}
		public void setQueueNo(int a) {
			this.queueNo = a;
		}
		public int getQueueNo() {
			return queueNo;
		}
		
		public int getWaiting() {
			return waiting;
		}

		public void setWaiting(int waiting) {
			this.waiting = waiting;
		}

		public int getRunning() {
			return running;
		}

		public void setRunning(int running) {
			this.running = running;
		}

		public ArrayList<Integer> getStart() {
			return start;
		}

		public void addStart(int start1) {
			start.add(start1);
		}
		public ArrayList<Integer> getEnd() {
			return end;
		}

		public void addEnd(int end1) {
			end.add(end1);
		}
		
		public Process getNextProcess(ArrayList<Process> processes) {
		    Process nextProcess = null;
		    int minRemainingTime = Integer.MAX_VALUE;
		    for (Process process : processes) {
		        int remainingTime = process.getRemainingTime();
		        if (remainingTime < minRemainingTime) {
		            minRemainingTime = remainingTime;
		            nextProcess = process;
		        }
		    }
		    return nextProcess;
		}

		@Override
		public String toString() {
			return "Process [pid=" + pid + ", arrivalTime=" + arrivalTime + ", cpuBursts=" + cpuBursts + ", ioBursts="
					+ Arrays.toString(ioBursts) + ", start=" + start + ", end=" + end + "]";
		}

		

		/*@Override
		public String toString() {
			return "Process [pid=" + pid + ", arrivalTime=" + arrivalTime + ", cpuBursts=" + cpuBursts + ", ioBursts="
					+ Arrays.toString(ioBursts) + ", waiting=" + waiting + "]";
		}*/
		
		
		

		
		
		

}
