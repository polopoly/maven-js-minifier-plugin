/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * Minifies and concatenates javascript files described in the descriptor file (packageFile).
 * 
 * @goal minify-js
 * @phase process-resources
 */
public class JsMinifierMojo
    extends AbstractMojo
{
    
    /**
     * Javascript source directory.
     * 
     * @parameter expression="${jsMinifier.sourceDir}" default-value="${project.basedir}/src/main/javascript"
     * @required
     */
    private String sourceDir;

    /**
     * Javascript minifier package file.
     * 
     * @parameter expression="${jsMinifier.descriptor}" default-value="${project.basedir}/src/main/javascript/package.js"
     * @required
     */
    private String packageFile;
    
    /**
     * Webapp target directory.
     * 
     * @parameter expression="${jsMinifier.targetDir}"
     *            default-value="${project.build.directory}/${project.build.finalName}"
     * @required
     */
    private String targetDir;

    
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        ScriptMinifier scriptMinifier = new ScriptMinifier();
        try {
            scriptMinifier.minify(sourceDir, targetDir, packageFile);
        }
        catch (Exception e) {
            throw new MojoExecutionException("Failed to minify js.", e);
        }
    }

}
