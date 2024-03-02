package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;

public class Json {
    public static String format(ArrayList<HashMap<String, Object>> diffObj) {
        return String.valueOf(diffObj);
    }
}
