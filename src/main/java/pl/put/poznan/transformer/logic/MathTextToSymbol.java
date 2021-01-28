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

public class MathTextToSymbol extends TextDecorator{
    public MathTextToSymbol(TextTransform t) {
        super(t);
    }
    public MathTextToSymbol(TextTransform t, JSONLoader loader) {
        super(t);
        this.loader = loader;
    }
    private static final Logger logger = LoggerFactory.getLogger(MathTextToSymbol.class);
    private JSONLoader loader;
    public List<List<String>> mathTextList;

    /**
     * Changes math text to symbol
     * @param text text that has text that are meant to be changed into symbols
     */
    @Override
    protected String operation(String text) {
        if(this.loader == null){
            loader = new JSONLoader();
            loader.readMathTextList();
        }
        mathTextList = loader.getJSONList();
        String copy;
        logger.debug("Math to symbol operation started.");
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
