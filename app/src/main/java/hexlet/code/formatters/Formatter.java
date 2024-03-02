package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;

public class Formatter {
    public static String format(ArrayList<HashMap<String, Object>> diffObj, String formatName) {
        return switch (formatName) {
            case "stylish" -> Stylish.format(diffObj);
            case "plain" -> Plain.format(diffObj);
            case "json" -> Json.format(diffObj);
            default -> throw new Error("Данный формат не поддерживается!");
        };
    }
}
