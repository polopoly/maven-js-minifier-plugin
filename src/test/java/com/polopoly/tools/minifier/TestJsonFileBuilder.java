/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;


public class TestJsonFileBuilder extends TestCase {

    private FileMapConstructor target;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        target = new FileMapConstructor();
    }
    
    public void testParseOKJson() throws Exception {
        
        String okJson = "{ \"common\": [\"ab\",\"cd\"], \"other\": [\"de\",\"fg\"] }";
        
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        
        List<String> common = new ArrayList<String>();
        common.add("ab"); common.add("cd");
        
        List<String> other = new ArrayList<String>();
        other.add("de"); other.add("fg");
        
        expected.put("common", common);
        expected.put("other", other);
        
        Map<String, List<String>> decoded = target.decodeFiles(okJson);

        assertEquals("JSON was parsed incorrectly", expected, decoded);
        
    }
    
}
