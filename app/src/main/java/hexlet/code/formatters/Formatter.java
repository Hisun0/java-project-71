package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;

public class Formatter {
    public static String format(ArrayList<HashMap<String, Object>> diffObj, String format) {
        switch (format) {
            case "stylish":
                return Stylish.format(diffObj);
            default:
                throw new Error("Данный формат не поддерживается!");
        }
    }
}
