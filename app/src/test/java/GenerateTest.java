import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateTest {
    private static final String[] FILE_NAMES_FOR_RESULT = {"stylishResult.txt", "plainResult.txt"};
    private static final String[] DIFFS_RESULT = new String[FILE_NAMES_FOR_RESULT.length];
    private static final String TEST_RESOURCES_DIR = "src/test/resources/";
    private static final String[] JSON_FILES = {"file1.json", "file2.json"};
    private static final String[] YAML_FILES = {"file1.yaml", "file2.yml"};

    @BeforeAll
    public static void getResult() throws IOException {
        for (int i = 0; i < FILE_NAMES_FOR_RESULT.length; i++) {
            Path filepath = Paths.get(TEST_RESOURCES_DIR, FILE_NAMES_FOR_RESULT[i]);
            DIFFS_RESULT[i] = Files.readString(filepath.toAbsolutePath().normalize());
        }
    }

    @Test
    public void stylishTestForJson() throws IOException {
        String stylishResult = DIFFS_RESULT[0];
        String filepath1 = getFilePath(JSON_FILES[0]);
        String filepath2 = getFilePath(JSON_FILES[1]);

        assertEquals(stylishResult, Differ.generate(filepath1, filepath2));
    }

    @Test
    public void stylishTestForYaml() throws IOException {
        String stylishResult = DIFFS_RESULT[0];
        String filepath1 = getFilePath(YAML_FILES[0]);
        String filepath2 = getFilePath(YAML_FILES[1]);

        assertEquals(stylishResult, Differ.generate(filepath1, filepath2));
    }

    @Test
    public void plainTestForJson() throws IOException {
        String plainResult = DIFFS_RESULT[1];
        String filepath1 = getFilePath(JSON_FILES[0]);
        String filepath2 = getFilePath(JSON_FILES[1]);

        assertEquals(plainResult, Differ.generate(filepath1, filepath2, "plain"));
    }

    @Test
    public void plainTestForYaml() throws IOException {
        String plainResult = DIFFS_RESULT[1];
        String filepath1 = getFilePath(YAML_FILES[0]);
        String filepath2 = getFilePath(YAML_FILES[1]);

        assertEquals(plainResult, Differ.generate(filepath1, filepath2, "plain"));
    }

    private static String getFilePath(String filename) {
        return Paths.get(TEST_RESOURCES_DIR, filename).toString();
    }
}
