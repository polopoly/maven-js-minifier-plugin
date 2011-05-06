/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DescriptionReader {

    private static String HEADER = "/** Begin package description */";
    private static String FOOTER = "/** End package description */";

    public String decode(File packageFile) throws IOException {
        StringBuffer res = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(packageFile));
        String str;
        while ((str = in.readLine()) != null) {
            res.append(str);
        }
        in.close();
        String parsed = res.toString();

        int start = parsed.indexOf(HEADER);
        int end = parsed.indexOf(FOOTER);
        
        if (start >= 0 && end > 0) {
            String substr = parsed.substring(start + HEADER.length() + 1, end).trim();
            substr = substr.substring(substr.indexOf('{'));
            if (substr.charAt(substr.length()-1) == ';') {
                return substr.substring(0, substr.length()-1);
            } else {
                return substr;
            }
        }
        return parsed;
    }
    
    
}
