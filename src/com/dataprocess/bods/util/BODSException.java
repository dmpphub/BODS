package com.dataprocess.bods.util;

/**
 * The Class BODSException.
 */
public class BODSException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8336295360592482246L;

    /** The class name. */
    private String className;

    /** The method name. */
    private String methodName;

    /** The exception. */
    private String exception;

    /**
     * Instantiates a new bODS exception.
     * 
     * @param className the class name
     * @param methodName the method name
     * @param exception the exception
     */
    public BODSException(String className, String methodName, String exception) {
        this.className = className;
        this.methodName = methodName;
        this.exception = exception;
    }

    /**
     * Gets the class name.
     * 
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name.
     * 
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the method name.
     * 
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets the method name.
     * 
     * @param methodName the new method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets the exception.
     * 
     * @return the exception
     */
    public String getException() {
        return exception;
    }

    /**
     * Sets the exception.
     * 
     * @param exception the new exception
     */
    public void setException(String exception) {
        this.exception = exception;
    }

}
