/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * Borrowed almost verbatim and stipped down 
 * from http://github.com/n0ha/yui-compressor-ant-task 
 */
public class YuiCompressorWrapper {

    protected String  charset               = "UTF-8";
    protected int     lineBreakPosition     = 2048;
    protected boolean munge                 = false;
    protected boolean warn                  = true;
    protected boolean preserveAllSemiColons = true;
    protected boolean optimize              = true;
        
    public YuiCompressorWrapper(String charset, int lineBreakPosition,
                                boolean munge,  boolean warn, 
                                boolean preserveAllSemiColons, boolean optimize) 
    {
        super();
        this.charset = charset;
        this.lineBreakPosition = lineBreakPosition;
        this.munge = munge;
        this.warn = warn;
        this.preserveAllSemiColons = preserveAllSemiColons;
        this.optimize = optimize;
    }
    
    public YuiCompressorWrapper() {
    }

    
    public String compressString(String in) {
        
        try {
            Reader inFile = new StringReader(in);
            StringWriter outFile = new StringWriter();

            JavaScriptCompressor compressor = createJavaScriptCompressor(inFile);
            compressor.compress(outFile, 
                                lineBreakPosition, 
                                munge, 
                                warn, 
                                preserveAllSemiColons, 
                                !optimize);
            return outFile.toString();
        } catch (IOException e) {
            // Log
        }
        return "";
    }
    
    private JavaScriptCompressor createJavaScriptCompressor(Reader in) throws IOException {
        JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {

            public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
            }

            public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
                throw new RuntimeException(message + ": " + lineSource);
            }

            public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
                return new EvaluatorException(message);
            }
        });
        return compressor;
    }
    
}
