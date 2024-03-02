package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Differ {
    public static String generate(String filepath1, String filepath2, String formatName) throws IOException {
        Map<String, Object> data1 = Parser.parse(filepath1);
        Map<String, Object> data2 = Parser.parse(filepath2);

        ArrayList<HashMap<String, Object>> diffObj = DifferenceBuilder.generateDiffObj(data1, data2);

        return Formatter.format(diffObj, formatName);
    }
}
