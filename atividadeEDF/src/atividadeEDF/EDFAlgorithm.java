package atividadeEDF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import resources.Task;

public class EDFAlgorithm {

	public static int totalExecutionTime = 0;
	
	public static void main(String[] args) throws Exception {
		ArrayList<Task> tasks = new ArrayList<>();
		String filename = "src/resources/inputs/sistema6Extra.txt"; // Nome do arquivo de entrada e caminho

		try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
			String line = file.readLine();// pula cabeçalhos
			int index = 01;

			while ((line = file.readLine()) != null) {
				String[] parts = line.split("\\s+");
				if (parts.length == 3) {
					int period = Integer.parseInt(parts[0]);
					int executionTime = Integer.parseInt(parts[1]);
					int deadline = Integer.parseInt(parts[2]);
					tasks.add(new Task("Tarefa0000" + index, period, executionTime, deadline));
					index++;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
			e.printStackTrace();
		}

		try {
			System.out.println("O Sistema é escalonável.");
			int hyperperiod = calculateHyperperiod(tasks);

			executeSchedule(tasks, hyperperiod);
			double updateRate = calculateUpdateRate(hyperperiod);
			System.out.println("Taxa de atualização é igual a " + String.format("%.3f", updateRate));
		}catch (Exception e) {
			System.out.flush();
			System.out.println("O Sistema não é escalonável.");
		}
	}
	
	private static void executeSchedule(List<Task> tasks, int hyperperiod) {
		PriorityQueue<Task> readyQueue = new PriorityQueue<>(Comparator.comparingInt(t -> t.getDeadlineRelative()));
		int currentTime = 0;
		
		//no tamanho do hiperperiodo
		while (currentTime < hyperperiod) {
			//Verifica se a task tá no limite e pode ser executada
			for (Task task : tasks) {
				if (verifyCanBeInQueue(task, currentTime)) {
					readyQueue.add(task);
				}
			}
			
			// se tiver tarefas para executar na fila
			if (!readyQueue.isEmpty()) {
				Task currentTask = readyQueue.poll();
				
				//Executa a tarefa e decrementa o seu tempo de execução necessário no período;
				currentTask.executationTimeRemainingMinus();
				int currentExecTime = currentTask.getExecutationTimeRemaining();
				totalExecutionTime++;
				
				System.out.println("Tempo " + currentTime + ": Executando o processo " + currentTask.getName());
				
				// se a tarefa já foi completa
				if (currentExecTime == 0) {
					if(currentTime > currentTask.getDeadlineRelative()) {
						System.out.println("Tempo " + currentTime + ": Processo " + currentTask.getName()
						+ " não conseguiu cumprir o prazo.");
						throw new RuntimeException("não conseguiu cumprir o prazo");
					}
				} 
				//se a tarefa não foi completada coloca ela de volta na fila
				else {
					readyQueue.add(currentTask);
				}
			}else {
				System.out.println("Tempo " + currentTime);
			}
			currentTime++;
		}
		System.out.println("Tempo " + hyperperiod + ": Todas as tarefas foram concluídas.");
	}

	private static int calculateHyperperiod(List<Task> tasks) {
		int hyperperiod = 1;

		for (Task task : tasks) {
			hyperperiod = mmc(hyperperiod, task.getPeriodAbsolute());
		}

		return hyperperiod;
	}

	private static double calculateUpdateRate(int hyperperiod) {
		return (double) totalExecutionTime / hyperperiod;
	}

	private static boolean verifyCanBeInQueue(Task task, int currentTime) {
		if(currentTime ==0) {
			return true; //caso seja a primeira entrada adiciona todas as tarefas
		}
		
		if(task.getPeriodRunTime() <= currentTime && task.getExecutationTimeRemaining() == 0) {
			task.setExecutationTimeRemaining(task.getExecutionTime());
			task.addDeadlineRunTime();
			task.addPeriodRunTime();
			return true;
		}else {
			return false;
		}
	}
	
	public static int mdc(int a, int b) {
		if (b == 0) { // se o segundo número é zero
			return a; // retornar o primeiro número
		} else { // se o segundo número não é zero
			return mdc(b, a % b); // retornar o MDC do segundo número e do resto da divisão do primeiro pelo
									// segundo
		}
	}

	// Método para calcular o mínimo múltiplo comum (MMC) de dois números
	public static int mmc(int a, int b) {
		return (a * b) / mdc(a, b); // retornar o produto dos números dividido pelo MDC deles
	}
}