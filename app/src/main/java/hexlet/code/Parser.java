package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    private static Map<String, Object> parseJson(Path filepath) throws IOException {
        String json = Files.readString(filepath);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, new TypeReference<>() {
        });
    }

    private static Map<String, Object> parseYaml(Path filepath) throws IOException {
        String yaml = Files.readString(filepath);

        ObjectMapper mapper = new YAMLMapper();

        return mapper.readValue(yaml, new TypeReference<>() {
        });
    }

    public static Map<String, Object> parse(String filepath) throws IOException {
        Path pathToFile = Path.of(filepath);
        String extension = FilenameUtils.getExtension(pathToFile.toString());

        return switch (extension) {
            case "json" -> parseJson(pathToFile);
            case "yaml", "yml" -> parseYaml(pathToFile);
            default -> throw new Error("Данный формат не поддерживается!");
        };
    }
}
