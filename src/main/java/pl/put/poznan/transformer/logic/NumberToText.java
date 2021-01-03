package pl.put.poznan.transformer.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * Class extending TextDecorator, it handles transforming number written in digits to number written in words.
 *
 */
public class NumberToText extends TextDecorator
{
    public NumberToText(TextTransform t) {
        super(t);
    }

    /**
     * Function transforming number written in digits to number written in words in polish language.
     * @param text is a String in which numbers are to be converted.
     * @return String with numbers converted to words.
     */
    @Override
    protected String operation(String text) {
        return replace(text);
    }

    private static String replace(String s) {
        StringBuilder result = new StringBuilder();
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        while (m.find())
        {
            m.appendReplacement(result,toText(m.group()));
        }
        m.appendTail(result);
        return result.toString();
    }

    private static String toText(String group) {
        long number = Long.parseLong(group);
        return translation(number).trim();
    }
    private static String translation(long number) {

        List<String> ones = readStringList("ones");

        List<String> teens = readStringList("teens");

        List<String> tens = readStringList("tens");

        List<String> hundreds = readStringList("hundreds");

        List<List<String>> groups = readStringListList("groups");

        long j = 0,
                n = 0,
                d = 0,
                s = 0,
                g = 0,
                k = 0;
        StringBuilder result = new StringBuilder();
        String sign = "";

        if (number < 0) {
            sign = "minus ";
            number = -number;
        }
        if (number == 0) {
            sign = "zero";
        }

        while (number != 0) {
            s = number % 1000 / 100;
            d = number % 100 / 10;
            j = number % 10;
            // special case if number is in teens
            if (d == 1 & j > 0)
            {
                n = j;
                d = 0;
                j = 0;
            } else {
                n = 0;
            }
            if (j == 1 & s + d + n == 0) {
                k = 0;
                if (s + d == 0 && g > 0) {
                    j = 0;
                    result.insert(0, groups.get((int) g).get((int) k));
                }
            } else if (j >=2 & j<=4) {
                k = 1;
            } else {
                k = 2;
            }

            if (s+d+n+j > 0) {
                result.insert(0, hundreds.get((int) s) + tens.get((int) d) + teens.get((int) n)
                        + ones.get((int) j) + groups.get((int) g).get((int) k));
            }

            number = number / 1000;
            g = g + 1;
        }

        result.insert(0, sign);
        return result.toString();
    }
    private static List<String> readStringList(String key){
        List<String> result = new ArrayList<>();
        JSONArray main = getParseArray("NumberToText.json");
        if (main == null) return result;
        for(Object mained: main)
        {
            JSONObject jsonContainer = (JSONObject) mained;
            if (jsonContainer.containsKey(key))
            {
                JSONArray array = (JSONArray) jsonContainer.get(key);
                for (Object o : array) {
                    result.add((String) o);
                }
                return result;
            }
        }
        return result;
    }
    private static List<List<String>> readStringListList(String key){
        List<List<String>> result = new ArrayList<>();
        JSONArray main = getParseArray("NumberToText.json");
        if (main == null) return result;
        for(Object mained: main)
            {
                JSONObject jsonContainer = (JSONObject) mained;
                if (jsonContainer.containsKey(key))
                {
                    JSONArray array = (JSONArray) jsonContainer.get(key);
                    for (Object nested : array)
                    {
                        JSONArray arrayNested = (JSONArray) nested;
                        List<String> nestedResult = new ArrayList<>();
                        for (Object o : arrayNested) {
                            nestedResult.add((String) o);
                        }
                        result.add(nestedResult);
                    }
                    return result;
                }
            }
        return result;
    }
    private static JSONArray getParseArray(String path){
        try {
            JSONParser jsonParser = new JSONParser();
            String filePath = new File("").getAbsolutePath();
            FileReader reader;
            reader = new FileReader(filePath+"\\src\\main\\resources\\" + path);
            Object obj = jsonParser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}