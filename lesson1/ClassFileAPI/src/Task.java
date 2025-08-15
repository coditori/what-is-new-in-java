import java.io.IOException;
import java.io.InputStream;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;

public class Task {
    public static void main(String[] args) throws IOException {
        try (InputStream in = Task.class.getResourceAsStream("/Task.class")) {
            if (in == null) {
                throw new IOException("Could not load Task.class as resource");
            }
            byte[] bytes = in.readAllBytes();
            ClassModel model = ClassFile.of().parse(bytes);
            System.out.println(model.thisClass().asInternalName());
        }
    }
} 