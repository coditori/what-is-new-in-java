import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class Task {

    public static void main(String[] args) throws IOException {

        // 1) Reader.of: optimized, non-synchronized reader avoiding copy and using bulk reads
        Reader optimized = Reader.of("hello");
        // 2) StringReader: synchronized per-read, incurs lock overhead compared to Reader.of
        Reader classic = new StringReader("hello");
        // (Other alternatives for context)
        char[] chars = "hello".toCharArray();
        Reader charArrayRead = new CharArrayReader(chars);
        Reader streamRead = new InputStreamReader(
            new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8))
        );
        // Consume one character from each to illustrate usage
        System.out.println(optimized.read());
        System.out.println(classic.read());
        System.out.println(charArrayRead.read());
        System.out.println(streamRead.read());


    }
}
