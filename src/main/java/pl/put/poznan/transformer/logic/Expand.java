package pl.put.poznan.transformer.logic;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.Character.isUpperCase;

/**
 * Provides implementation of expand operation and a method for reading JSON file containing collapsed and extended versions
 * of phrases.
 */
public class Expand extends TextDecorator {

    public Expand(TextTransform t) {
        super(t);
    }
    public Expand(TextTransform t, JSONLoader loader){ super(t); this.loader = loader;}
    private static final Logger logger = LoggerFactory.getLogger(NumberToText.class);
    private JSONLoader loader;
    private List<List<String>> expandCollapseList;

    /**
     * Changes are occurences of pre-definied phrases to their longer versions. If
     * first letter of occurence is upper-case then longer version of it will also be. If whole occurence is upper-case then
     * longer version will be too.
     * @param text text that has phrases that are meant to be expanded
     */
    @Override
    protected String operation(String text) {
        if(this.loader == null){
            loader = new JSONLoader();
            loader.readExpandCollapseList();
        }
        expandCollapseList = loader.getJSONList();
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
