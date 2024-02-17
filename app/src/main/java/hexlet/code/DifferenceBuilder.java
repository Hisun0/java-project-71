package hexlet.code;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class DifferenceBuilder {
    private static ArrayList<String> getUnionKeys(
            Map<String, Object> data1, Map<String, Object> data2
    ) {
        Set<String> keys = new HashSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        ArrayList<String> listKeys = new ArrayList<>(keys);
        Collections.sort(listKeys);

        return listKeys;
    }

    private static HashMap<String, Object> getDifferenceResult(
            Map<String, Object> data1, Map<String, Object> data2, String key
    ) {
        String value1 = String.valueOf(data1.get(key));
        String value2 = String.valueOf(data2.get(key));

        if (!data2.containsKey(key)) {
            return new HashMap<>(Map.of("key", key, "value", value1, "status", "deleted"));
        } else if (!data1.containsKey(key)) {
            return new HashMap<>(Map.of("key", key, "value", value2, "status", "added"));
        } else if (!value1.equals(value2)) {
            return (new HashMap<>(Map.of(
                    "key", key,
                    "oldValue", value1,
                    "newValue", value2,
                    "status", "changed"
            )));
        }

        return new HashMap<>(Map.of("key", key, "value", value1, "status", "unchanged"));
    }

    public static ArrayList<HashMap<String, Object>> generateDiffObj(
            Map<String, Object> data1, Map<String, Object> data2
    ) {
        ArrayList<String> listKeys = getUnionKeys(data1, data2);

        return listKeys.stream()
                .map((key) -> getDifferenceResult(data1, data2, key))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
