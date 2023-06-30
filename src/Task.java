public class Task {
    String name;
    int releaseTime;
    int executionTime;
    int deadline;
    int period;

    public Task(String name, int releaseTime, int executionTime, int deadline, int period) {
        this.name = name;
        this.releaseTime = releaseTime;
        this.executionTime = executionTime;
        this.deadline = deadline;
        this.period = period;
    }
}