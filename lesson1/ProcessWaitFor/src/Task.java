import java.time.Duration;

public class Task {
	public static void main(String[] args) throws Exception {
		Process p = new ProcessBuilder("sh", "-c", "echo hi").start();
		boolean finished = p.waitFor(Duration.ofSeconds(5));
		System.out.println("finished=" + finished + ", exit=" + p.exitValue());
	}
} 