import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EDF {
    public static int hyperperiod(List<Task> tasks) {
        int max = 0;
        for (Task task : tasks) {
            if (task.period > max) {
                max = task.period;
            }
        }
        return max;
    }

    public static boolean isSchedulable(List<Task> tasks) {
        double U = 0;
        for (Task task : tasks) {
            U += (double) task.executionTime / task.period;
        }
        return U <= tasks.size() * (Math.pow(2, 1.0 / tasks.size()) - 1);
    }

    public static List<String> EDF(List<Task> tasks, int hp) {
        List<String> schedule = new ArrayList<>();
        int time = 0;
        while (time < hp) {
            Task earliestDeadlineTask = null;
            for (Task task : tasks) {
                if (task.releaseTime <= time) {
                    if (earliestDeadlineTask == null || task.deadline < earliestDeadlineTask.deadline) {
                        earliestDeadlineTask = task;
                    }
                }
            }
            if (earliestDeadlineTask != null) {
                schedule.add(earliestDeadlineTask.name);
                time += earliestDeadlineTask.executionTime;
                earliestDeadlineTask.deadline += earliestDeadlineTask.period;
                earliestDeadlineTask.releaseTime += earliestDeadlineTask.period;
            } else {
                schedule.add("IDLE");
                time++;
            }
        }
        return schedule;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(new File("tasks.txt"));
        scanner.nextLine(); // skip header line
        int i = 1; // task name counter
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\\s+");
            String name = "T" + i++;
            int period = Integer.parseInt(line[0]);
            int executionTime = Integer.parseInt(line[1]);
            int relativeDeadline = Integer.parseInt(line[2]);
            tasks.add(new Task(name, 0, executionTime, relativeDeadline, period));
        }
        scanner.close();

        int hp = hyperperiod(tasks);

        if (isSchedulable(tasks)) {
            List<String> schedule = EDF(tasks, hp);
            System.out.println("Schedule: " + schedule);
        } else {
            System.out.println("Tasks are not schedulable");
        }
    }
}
