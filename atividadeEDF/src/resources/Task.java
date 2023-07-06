package resources;

public class Task {
	
	private String name;
    
	private Integer period;
    
	private Integer periodRunTime;
    
    private int executionTime;
    
    private int executationTimeRemaining;
    
    private int deadlineRunTime;
    
    private int deadline;

   public Task(String name, Integer period, int executionTime, int deadline) {
	   this.name = name;
        this.period = period;
        this.periodRunTime= period;
        this.executionTime = executionTime;
        this.executationTimeRemaining = executionTime;
        this.deadline = deadline;
        this.deadlineRunTime = deadline;
    }

	public int getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}
	
	public void setExecutionTimeMinus() {
		this.executionTime--;
	}

	public int getDeadlineRelative() {
		return deadlineRunTime;
	}

	public void setDeadlineRuntime(int deadlineRelative) {
		this.deadlineRunTime = deadlineRelative;
	}
	
	public void addDeadlineRunTime() {
		this.deadlineRunTime += this.deadline;
	}
	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadlineAbsolute) {
		this.deadline = deadlineAbsolute;
	}
	
	public Integer getPeriodAbsolute() {
		return period;
	}

	public void setPeriod(Integer periodAbsolute) {
		this.period = periodAbsolute;
	}

	public Integer getPeriodRunTime() {
		return periodRunTime;
	}

	public void setPeriodRelative(Integer periodRelative) {
		this.periodRunTime = periodRelative;
	}

	public void addPeriodRunTime() {
		this.periodRunTime += this.period;
	}
	
	public int getExecutationTimeRemaining() {
		return executationTimeRemaining;
	}

	public void setExecutationTimeRemaining(int executationTimeRemaining) {
		this.executationTimeRemaining = executationTimeRemaining;
	}

	public void executationTimeRemainingMinus() {
		this.executationTimeRemaining--;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
