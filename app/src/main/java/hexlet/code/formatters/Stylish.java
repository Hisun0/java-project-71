package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;

public class Stylish {
    private static final String LABEL_ADD = "+ ";
    private static final String LABEL_DELETED = "- ";
    private static final String LABEL_UNCHANGED = "  ";
    private static final String REPLACER = " ";

    private static void makeDiffString(StringBuilder stringBuilder, String label, String key, String value) {
        String space = REPLACER.repeat(2);
        stringBuilder
                .append(space)
                .append(label)
                .append(key)
                .append(": ")
                .append(value)
                .append("\n");
    }

    public static String format(ArrayList<HashMap<String, Object>> diffObj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("\n");

        diffObj.forEach((obj) -> {
            String key = String.valueOf(obj.get("key"));
            String value = String.valueOf(obj.get("value"));

            switch (obj.get("status").toString()) {
                case "deleted":
                    makeDiffString(stringBuilder, LABEL_DELETED, key, value);
                    break;
                case "added":
                    makeDiffString(stringBuilder, LABEL_ADD, key, value);
                    break;
                case "changed":
                    makeDiffString(stringBuilder, LABEL_DELETED, key, obj.get("oldValue").toString());
                    makeDiffString(stringBuilder, LABEL_ADD, key, obj.get("newValue").toString());
                    break;
                default:
                    makeDiffString(stringBuilder, LABEL_UNCHANGED, key, value);
            }
        });

        stringBuilder.append("}");

        return String.valueOf(stringBuilder);
    }
}
