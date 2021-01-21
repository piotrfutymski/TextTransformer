package pl.put.poznan.transformer.logic;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.util.Arrays.asList;

public class SymbolToMathText extends TextDecorator{
    public SymbolToMathText(TextTransform t) {
        super(t);
    }
    private static final Logger logger = LoggerFactory.getLogger(MathTextToSymbol.class);
    public List<List<String>> mathTextList;

    /**
     * Reads math-text.json file. Sets private class variable mathTextList.
     */
    public void readExpandCollapseList(){
        logger.debug("Reading from JSON file started.");
        mathTextList = new ArrayList<List<String>>();
        JSONParser jsonParser = new JSONParser();
        try{
            //String filePath = new File("").getAbsolutePath();
            InputStream input = getClass().getResourceAsStream("/math-text.json");
            Reader reader = new InputStreamReader(input);
            Object obj = jsonParser.parse(reader);
            JSONArray equalityList = (JSONArray) obj;
            for(Object equality: equalityList){
                JSONObject jsonContainer = (JSONObject) equality;
                JSONObject jsonEquality = (JSONObject) jsonContainer.get("equality");
                String text = (String) jsonEquality.get("text");
                String symbol = (String) jsonEquality.get("symbol");
                mathTextList.add(new ArrayList<String>(asList(symbol, text)));
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
        logger.debug("Reading from JSON file finished.");
    }

    /**
     * Changes math text to symbol
     * @param text text that has text that are meant to be changed into symbols
     */
    @Override
    protected String operation(String text) {
        readExpandCollapseList();
        String copy;
        logger.debug("Expand operation started.");
        for(List<String> equality: mathTextList){
            int index;
            copy = text.toLowerCase();
            while((index = copy.indexOf(equality.get(0))) != - 1) {
                logger.debug("Occurence found at index: " + index);
                text = StringUtils.replaceOnceIgnoreCase(text, equality.get(0), equality.get(1));
                copy = text.toLowerCase();
            }
        }
        logger.debug("Symbols created.");
        return text;
    }
}