package pl.put.poznan.transformer.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Character.isUpperCase;
import static java.util.Arrays.asList;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides implementation of expand operation and a method for reading JSON file containing collapsed and extended versions
 * of phrases.
 */
public class Expand extends TextDecorator {

    public Expand(TextTransform t) {
        super(t);
    }
    private static final Logger logger = LoggerFactory.getLogger(NumberToText.class);
    private List<List<String>> expandCollapseList;

    /**
     * Reads expand-collapse.json file. Sets private class variable expandCollapseList.
     */
    private void readExpandCollapseList(){
        logger.debug("Reading from JSON file started.");
        expandCollapseList = new ArrayList<List<String>>();
        JSONParser jsonParser = new JSONParser();
        try{
            String filePath = new File("").getAbsolutePath();
            FileReader reader = new FileReader(filePath+"\\src\\main\\resources\\expand-collapse.json");
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
        logger.debug("Reading from JSON file finished.");
    }

    /**
     * Changes are occurences of pre-definied phrases to their longer versions. If
     * first letter of occurence is upper-case then longer version of it will also be. If whole occurence is upper-case then
     * longer version will be too.
     * @param text text that has phrases that are meant to be expanded
     */
    @Override
    protected String operation(String text) {
        readExpandCollapseList();
        String copy;
        logger.debug("Expand operation started.");
        for(List<String> equality: expandCollapseList){
            int index;
            copy = text.toLowerCase();
            while((index = copy.indexOf(equality.get(0))) != - 1) {
                logger.debug("Occurence found at index: " + index);
                if((text.substring(index, index + equality.get(0).length())).toUpperCase().equals(text.substring(index, index + equality.get(0).length()))){
                    text = StringUtils.replaceOnce(text, equality.get(0).toUpperCase(), equality.get(1).toUpperCase());
                }
                else if(isUpperCase(text.charAt(index))){
                    text = StringUtils.replaceOnceIgnoreCase(text, equality.get(0).substring(0, 1).toUpperCase()+equality.get(0).substring(1), equality.get(1).substring(0, 1).toUpperCase()+equality.get(1).substring(1));
                }
                else{
                    text = StringUtils.replaceOnceIgnoreCase(text, equality.get(0), equality.get(1));
                }
                copy = text.toLowerCase();
            }
        }
        logger.debug("Expanded text created.");
        return text;
    }
}
