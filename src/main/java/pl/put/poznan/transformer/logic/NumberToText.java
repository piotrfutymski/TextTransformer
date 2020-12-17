package pl.put.poznan.transformer.logic;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

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
     * Function transforming number written in digits to number written in words in language polish.
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
        List<String> ones = Arrays.asList( "", "jeden ", "dwa ", "trzy ", "cztery ",
                "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ");

        List<String> teens = Arrays.asList( "", "jedenaście ", "dwanaście ", "trzynaście ",
                "czternaście ", "piętnaście ", "szesnaście ", "siedemnaście ",
                "osiemnaście ", "dziewiętnaście ");

        List<String> tens = Arrays.asList( "", "dziesięć ", "dwadzieścia ",
                "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
                "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
                "dziewięćdziesiąt ");

        List<String> hundreds = Arrays.asList( "", "sto ", "dwieście ", "trzysta ", "czterysta ",
                "pięćset ", "sześćset ", "siedemset ", "osiemset ",
                "dziewięćset ");

        String[][] groups = { { "", "", "" },
                { "tysiąc ", "tysiące ", "tysięcy " },
                { "milion ", "miliony ", "milionów " },
                { "miliard ", "miliardy ", "miliardów " },
                { "bilion ", "biliony ", "bilionów " },
                { "biliard ", "biliardy ", "biliardów " },
                { "trylion ", "tryliony ", "trylionów " }, };

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
                    result.insert(0, groups[(int) g][(int) k]);
                }
            } else if (j >=2 & j<=4) {
                k = 1;
            } else {
                k = 2;
            }

            if (s+d+n+j > 0) {
                result.insert(0, hundreds.get((int) s) + tens.get((int) d) + teens.get((int) n)
                        + ones.get((int) j) + groups[(int) g][(int) k]);
            }

            number = number / 1000;
            g = g + 1;
        }

        result.insert(0, sign);
        return result.toString();
    }
}
    /**
    private void readData(String key){
        JSONParser jsonParser = new JSONParser();
        List<String> result = new List<String>;
        try{
            String filePath = new File("").getAbsolutePath();
            FileReader reader = new FileReader(filePath+"\\src\\main\\resources\\NumberToText.json");
            Object obj = jsonParser.parse(reader);
            JSONArray equalityList = (JSONArray) obj;
            for(Object equality: equalityList){
                JSONObject jsonContainer = (JSONObject) equality;
                JSONObject jsonEquality = (JSONObject) jsonContainer.get("equality");
                String collapsed = (String) jsonEquality.get("collapsed");
                String extended = (String) jsonEquality.get("extended");
                expandCollapseList.add(new ArrayList<String>(asList(collapsed, extended)));
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }
}
     */