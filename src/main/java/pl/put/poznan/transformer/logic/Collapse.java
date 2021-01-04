package pl.put.poznan.transformer.logic;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.util.Arrays.asList;

/**
 * Provides implementation of collapse operation and a method for reading JSON file containing collapsed and extended versions
 * of phrases.
 */
public class Collapse extends TextDecorator {

    public Collapse(TextTransform t) {
        super(t);
    }

    private List<List<String>> expandCollapseList;

    /**
     * Reads expand-collapse.json file. Sets private class variable expandCollapseList.
     */
    private void readExpandCollapseList(){
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
    }

    @Override
    /**
     * Changes are occurences of pre-definied phrases to their short versions. If
     * first letter of occurence is upper-case then shorter version of it will also be. If whole occurence is upper-case then
     * shorter version will be too.
     * @param text text that has phrases that are meant to be collapsed
     */
    protected String operation(String text) {
        readExpandCollapseList();
        String copy;
        for(List<String> equality: expandCollapseList){
            int index;
            copy = text.toLowerCase();
            while((index = copy.indexOf(equality.get(1))) != - 1) {
                if((text.substring(index, index + equality.get(1).length())).toUpperCase().equals(text.substring(index, index + equality.get(1).length()))){
                    text = StringUtils.replaceOnce(text, equality.get(1).toUpperCase(), equality.get(0).toUpperCase());
                }
                else if(isUpperCase(text.charAt(index))){
                    text = StringUtils.replaceOnceIgnoreCase(text, equality.get(1).substring(0, 1).toUpperCase()+equality.get(1).substring(1), equality.get(0).substring(0, 1).toUpperCase()+equality.get(0).substring(1));
                }
                else{
                    text = StringUtils.replaceOnceIgnoreCase(text, equality.get(1), equality.get(0));
                }
                copy = text.toLowerCase();
            }
        }
        return text;
    }
}
