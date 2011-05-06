/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileMapConstructor {

    public Map<String, List<String>> decodeFiles(String json) throws BadJsonException {
        
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        JSONParser parser = new JSONParser();
        try {
            Object parsed = parser.parse(json);
            if (parsed instanceof JSONObject) {
                JSONObject fileMap = (JSONObject) parsed;
                for (Object fileObj: fileMap.keySet()) {
                    String filename = fileObj.toString();
                    Object parsedArray = fileMap.get(fileObj);
                    if (parsedArray instanceof JSONArray) {
                    result.put(filename, 
                               getFilesFromMap((JSONArray) parsedArray));
                    }
                }
            } else {
                throw new BadJsonException("Top level object not a string");
            }
        } catch (ParseException e) {
            throw new BadJsonException(e);
        }
        return result;
    }

    private ArrayList<String> getFilesFromMap(JSONArray fileArray) 
    {
        ArrayList<String> outfiles = new ArrayList<String>();   
        for (Object f: fileArray) {
            outfiles.add(f.toString());
        }        
        return outfiles;
    }
    
    
}
