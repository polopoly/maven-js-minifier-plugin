/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FileConcatenator {

    private StringBuffer result = new StringBuffer();
    
    public void addFiles(String baseDir, List<String> filenames) 
        throws IOException 
    {
        for (String filename: filenames) {
            addFile(new File(baseDir, filename + ".js"));
        }
    }
        
    public void addFile(File file) 
        throws IOException 
    {
        
        InputStreamReader fileReader = null; 

        try {
            fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");

            int ch; 
            while ((ch = fileReader.read()) > -1) {
                result.append((char) ch);
            }
            
            return;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException io) {
                    // Log?
                }
            }
        }
    }
    
    public String getResult() {
        return result.toString();
    }

}
