package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;

public class Plain {
    private static final String ADDED = "added";
    private static final String DELETED = "removed";
    private static final String CHANGED = "updated";

    private static boolean isComplexValue(String value) {
        return value.startsWith("{") && value.endsWith("}")
                || value.startsWith("[") && value.endsWith("]");
    }

    private static boolean isBoolean(String value) {
        return value.equals("false") || value.equals("true");
    }

    private static boolean isNull(String value) {
        return value.equals("null");
    }

    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static String getFormattedValue(String value) {
        if (isComplexValue(value)) {
            return "[complex value]";
        }

        if (isBoolean(value) || isInteger(value) || isNull(value)) {
            return value;
        }

        return "'" + value + "'";
    }

    public static String format(ArrayList<HashMap<String, Object>> diffObj) {
        StringBuilder stringBuilder = new StringBuilder();

        diffObj.forEach((obj) -> {
            String key = String.valueOf(obj.get("key"));
            String value = String.valueOf(obj.get("value"));

            switch (obj.get("status").toString()) {
                case "deleted":
                    stringBuilder.append(String.format(
                            "Property '%1$s' was %2$s",
                            key, DELETED
                    )).append("\n");
                    break;
                case "added":
                    stringBuilder.append(String.format(
                            "Property '%1$s' was %2$s with value: %3$s",
                            key, ADDED, getFormattedValue(value)
                    )).append("\n");
                    break;
                case "changed":
                    stringBuilder.append(String.format(
                            "Property '%1$s' was %2$s. From %3$s to %4$s",
                            key, CHANGED,
                            getFormattedValue(String.valueOf(obj.get("oldValue"))),
                            getFormattedValue(String.valueOf(obj.get("newValue")))
                    )).append("\n");
                    break;
                default:
                    break;
            }
        });

        return stringBuilder.toString().trim();
    }
}
