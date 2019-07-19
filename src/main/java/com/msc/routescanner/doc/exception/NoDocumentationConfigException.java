/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msc.routescanner.doc.exception;

/**
 *
 * @author Michael
 */
public class NoDocumentationConfigException extends Exception {

    public NoDocumentationConfigException() {
    }

    public NoDocumentationConfigException(String message) {
        super(message);
    }

    public NoDocumentationConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDocumentationConfigException(Throwable cause) {
        super(cause);
    }

}
