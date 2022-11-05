package org.example.model;

/**
 * Custom exception class that throws error about incorrect command line argument type
 * @author klaud
 * @version  version1
 */
public class GraphSizeException extends Exception {


    /**
     * Public constructor.
     * @param errorMessage string containing error message
     */
    public GraphSizeException(String errorMessage) {
        super(errorMessage);
    }
}
