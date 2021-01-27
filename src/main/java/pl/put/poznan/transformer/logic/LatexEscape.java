package pl.put.poznan.transformer.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class LatexEscape extends TextDecorator {

    private Map<String,String> latexCharMap;
    /**
     * Constructor of LatexEscape
     * @param t transformation that will be performed after the operation
     */
    LatexEscape(TextTransform t) {
        super(t);
    }

    /**
     * Function that escapes Latex special characters
     * @param text input string
     * @return text with Latex special characters escaped
     */
    @Override
    protected String operation(String text) {
        String[] res = { text };
        if(latexCharMap == null)
            loadLatexEscapeInfo();
        latexCharMap.forEach((from, to) ->{
            Matcher matcher = Pattern.compile(Pattern.quote(from)).matcher(res[0]);
            res[0] = matcher.replaceAll(matcher.quoteReplacement(to));
        });
        return res[0];
    }

    private void loadLatexEscapeInfo()
    {
        JSONParser jsonParser = new JSONParser();
        latexCharMap = new HashMap<String,String>();
        try{
            InputStream input = getClass().getResourceAsStream("/LatexEscapeInfo.json");
            Reader reader = new InputStreamReader(input);
            JSONArray charMap = (JSONArray)jsonParser.parse(reader);
            for(Object it: charMap){
                JSONObject jContainer = (JSONObject) it;
                latexCharMap.put((String)jContainer.get("from"), (String) jContainer.get("to"));
            }
            reader.close();
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
