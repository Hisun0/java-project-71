import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateTest {
    private static String stylishResult;
    private static final String JSON_PATH1 = "src/test/resources/file1.json";
    private static final String JSON_PATH2 = "src/test/resources/file2.json";

    @BeforeAll
    public static void getResult() throws IOException {
        Path filepath = Paths.get("src", "test", "resources", "stylishResult.txt");

        stylishResult = Files.readString(filepath.toAbsolutePath().normalize());
    }

    @Test
    public void stylishJsonTest() throws IOException {
        assertEquals(stylishResult, Differ.generate(JSON_PATH1, JSON_PATH2));
    }
}
