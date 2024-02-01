package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Differ {
    public static Map<String, String> parse(String filepath1) throws IOException {
        Path pathToFile = Path.of(filepath1);

        String json = Files.readString(pathToFile);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    public static ArrayList<HashMap<String, String>> generateDiffObj(Map<String, String> data1, Map<String, String> data2) {
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

    public static void generate(String filepath1, String filepath2) throws IOException {
        Map<String, String> data1 = parse(filepath1);
        Map<String, String> data2 = parse(filepath2);

        ArrayList<HashMap<String, String>> diffObj = generateDiffObj(data1, data2);

        Map<String, String> labels = Map.of(
                "deleted", "- ",
                "added", "+ ",
                "changed", "  ",
                    "unchanged", "  "
        );

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("\n");

        String space = "  ";

        diffObj.forEach((obj) -> obj.forEach((key, value) -> {
            switch (value) {
                case "deleted":
                    stringBuilder.append(space).append(labels.get(value)).append(key).append(": ").append(data1.get(key)).append("\n");
                    break;
                case "added":
                    stringBuilder.append(space).append(labels.get(value)).append(key).append(": ").append(data2.get(key)).append("\n");
                    break;
                case "changed":
                    stringBuilder
                            .append(space)
                            .append(labels.get("deleted"))
                            .append(key)
                            .append(": ")
                            .append(data1.get(key))
                            .append("\n")
                            .append(space)
                            .append(labels.get("added"))
                            .append(key)
                            .append(": ")
                            .append(data2.get(key))
                            .append("\n");
                    break;
                default:
                    stringBuilder.append(space).append(labels.get(value)).append(key).append(": ").append(data1.get(key)).append("\n");
            }
        }));

        stringBuilder.append("}");
        System.out.println(stringBuilder);
    }
}
