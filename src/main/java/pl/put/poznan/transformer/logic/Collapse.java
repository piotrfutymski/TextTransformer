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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides implementation of collapse operation and a method for reading JSON file containing collapsed and extended versions
 * of phrases.
 */
public class Collapse extends TextDecorator {

    public Collapse(TextTransform t) {
        super(t);
    }
    public Collapse(TextTransform t, JSONLoader loader) {
        super(t);
        this.loader = loader;
    }
    private static final Logger logger = LoggerFactory.getLogger(NumberToText.class);
    private JSONLoader loader;
    private List<List<String>> expandCollapseList;

    @Override
    /**
     * Changes are occurences of pre-definied phrases to their short versions. If
     * first letter of occurence is upper-case then shorter version of it will also be. If whole occurence is upper-case then
     * shorter version will be too.
     * @param text text that has phrases that are meant to be collapsed
     */
    protected String operation(String text) {
        if(this.loader == null){
            loader = new JSONLoader();
            loader.readExpandCollapseList();
        }
        expandCollapseList = loader.getJSONList();
        String copy;
        logger.debug("Collapse operation started.");
        for(List<String> equality: expandCollapseList){
            int index;
            copy = text.toLowerCase();
            while((index = copy.indexOf(equality.get(1))) != - 1) {
                logger.debug("Occurence found at index: " + index);
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
        logger.debug("Collapsed text created.");
        return text;
    }
}
