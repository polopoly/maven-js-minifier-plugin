/**
 * Copyright (c) Polopoly AB (publ).
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
package com.polopoly.tools.minifier;

@SuppressWarnings("serial")
public class BadJsonException extends Exception {

    public BadJsonException() {
        super();
    }

    public BadJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadJsonException(String message) {
        super(message);
    }

    public BadJsonException(Throwable cause) {
        super(cause);
    }

    
}
