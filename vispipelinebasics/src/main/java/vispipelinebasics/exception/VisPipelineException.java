/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vispipelinebasics.exception;

/**
 *
 * @author PC
 */
public class VisPipelineException extends Exception {

    public VisPipelineException(String message) {
        super(message);
    }

    public VisPipelineException(Exception e) {
        super(e);
    }

    public VisPipelineException(String message, Exception e) {
        super(message, e);
    }
}
