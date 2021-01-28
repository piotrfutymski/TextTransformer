package pl.put.poznan.transformer.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

public class JSONLoader {
    private List<List<String>> JSONList;
    private static final Logger logger = LoggerFactory.getLogger(NumberToText.class);

    /**
     * Returns data from currently read file
     *
     * @return two-dimensional list of read items from JSON file
     */
    public List<List<String>> getJSONList() {
        return JSONList;
    }

    /**
     * Reads expand-collapse.json file. Sets private class variable expandCollapseList.
     */
    public void readExpandCollapseList() {
        logger.debug("Reading from JSON file started.");
        JSONList = new ArrayList<List<String>>();
        JSONParser jsonParser = new JSONParser();
        try {
            //String filePath = new File("").getAbsolutePath();
            InputStream input = getClass().getResourceAsStream("/expand-collapse.json");
            Reader reader = new InputStreamReader(input);
            Object obj = jsonParser.parse(reader);
            JSONArray equalityList = (JSONArray) obj;
            for (Object equality : equalityList) {
                JSONObject jsonContainer = (JSONObject) equality;
                JSONObject jsonEquality = (JSONObject) jsonContainer.get("equality");
                String collapsed = (String) jsonEquality.get("collapsed");
                String extended = (String) jsonEquality.get("extended");
                JSONList.add(new ArrayList<String>(asList(collapsed, extended)));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.debug("Reading from JSON file finished.");
    }

    /**
     * Reads math-text.json file. Sets private class variable mathTextList.
     */
    public void readMathTextList() {
        logger.debug("Reading from JSON file started.");
        JSONList = new ArrayList<List<String>>();
        JSONParser jsonParser = new JSONParser();
        try {
            //String filePath = new File("").getAbsolutePath();
            InputStream input = getClass().getResourceAsStream("/math-text.json");
            Reader reader = new InputStreamReader(input);
            Object obj = jsonParser.parse(reader);
            JSONArray equalityList = (JSONArray) obj;
            for (Object equality : equalityList) {
                JSONObject jsonContainer = (JSONObject) equality;
                JSONObject jsonEquality = (JSONObject) jsonContainer.get("equality");
                String text = (String) jsonEquality.get("text");
                String symbol = (String) jsonEquality.get("symbol");
                JSONList.add(new ArrayList<String>(asList(text, symbol)));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
