package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Differ {
    private static final String LABEL_ADD = "+ ";
    private static final String LABEL_DELETED = "- ";
    private static final String LABEL_UNCHANGED = "+ ";
    public static Map<String, String> parse(String filepath1) throws IOException {
        Path pathToFile = Path.of(filepath1);

        String json = Files.readString(pathToFile);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    public static ArrayList<HashMap<String, String>> generateDiffObj(
            Map<String, String> data1, Map<String, String> data2
    ) {
        Set<String> keys = new HashSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        ArrayList<String> listKeys = new ArrayList<>(keys);
        Collections.sort(listKeys);

        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        listKeys.forEach((key) -> {
            if (!data2.containsKey(key)) {
                result.add(new HashMap<>(Map.of(key, "deleted")));
            } else if (!data1.containsKey(key)) {
                result.add(new HashMap<>(Map.of(key, "added")));
            } else if (!data1.get(key).equals(data2.get(key))) {
                result.add(new HashMap<>(Map.of(
                        key, "changed"
                    )));
            } else {
                result.add(new HashMap<>(Map.of(key, "unchanged")));
            }
        });

        return result;
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        Map<String, String> data1 = parse(filepath1);
        Map<String, String> data2 = parse(filepath2);

        ArrayList<HashMap<String, String>> diffObj = generateDiffObj(data1, data2);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("\n");

        diffObj.forEach((obj) -> obj.forEach((key, value) -> {
            switch (value) {
                case "deleted":
                    makeDiffString(stringBuilder, LABEL_DELETED, key, data1.get(key));
                    break;
                case "added":
                    makeDiffString(stringBuilder, LABEL_ADD, key, data2.get(key));
                    break;
                case "changed":
                    makeDiffString(stringBuilder, LABEL_DELETED, key, data1.get(key));
                    makeDiffString(stringBuilder, LABEL_ADD, key, data2.get(key));
                    break;
                default:
                    makeDiffString(stringBuilder, LABEL_UNCHANGED, key, data1.get(key));
            }
        }));

        stringBuilder.append("}");
        System.out.println(stringBuilder);

        return String.valueOf(stringBuilder);
    }

    private static void makeDiffString(StringBuilder stringBuilder, String label, String key, String value) {
        String space = "  ";
        stringBuilder
                .append(space)
                .append(label)
                .append(key)
                .append(": ")
                .append(value)
                .append("\n");
    }
}
