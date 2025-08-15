import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Task {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4)
            .gather(Gatherers.windowFixed(2))
            .forEach(window -> System.out.println(window));
    }
} 