/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ScriptMinifier 
{
    private static final String JS_SUFFIX = ".js";
    
    private DescriptionReader descriptionReader;
    private FileMapConstructor fileMapper;
    private YuiCompressorWrapper ycw;
    
    public ScriptMinifier() 
    {
        fileMapper = new FileMapConstructor();
        descriptionReader = new DescriptionReader();
        ycw = new YuiCompressorWrapper();
    }

    public void minify(String inputDir, String outputDir, String packageFile) 
        throws IOException 
    {
        File pf;
        
        pf = new File(packageFile);
        if (!pf.exists()) {
            pf = new File(inputDir, packageFile);
        }
        if (!pf.exists()) {
            throw new IOException(String.format("Could not find package file, tried %s and %s.",
                                                packageFile, pf));
        }
        
        Map<String, List<String>> decodedFileList;
        try {
            decodedFileList = fileMapper.decodeFiles(descriptionReader.decode(pf));
        }
        catch (BadJsonException e) {
            throw new IOException("Failed to decode " + pf + ": " + e.getMessage());
        }
        
        for (String file: decodedFileList.keySet()) {           
            FileConcatenator concat = new FileConcatenator();
            concat.addFiles(inputDir, decodedFileList.get(file));
            String concatenatedFiles = concat.getResult();
            String minified = ycw.compressString(concatenatedFiles);
            
            writeMinifiedOutput(new File(outputDir, file + JS_SUFFIX), minified);
            writeMinifiedOutput(new File(outputDir, file + "-src" + JS_SUFFIX), concatenatedFiles);
        }        
    }

    private void writeMinifiedOutput(File file, String minifiedJs)
        throws IOException 
    {
        BufferedWriter bf = null;
        try {
            file.getParentFile().mkdirs();
            bf = new BufferedWriter(new FileWriter(file));
            bf.write(minifiedJs);
        } 
        finally {
            if (bf != null) {
                bf.close();
            }
        }
    }
    
}
