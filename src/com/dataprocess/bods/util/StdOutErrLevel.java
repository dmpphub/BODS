package com.dataprocess.bods.util;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.util.logging.Level;


/**
 * The Class StdOutErrLevel.
 */
public final class StdOutErrLevel extends Level {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new std out err level.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	private StdOutErrLevel(String name, int value) {
        super(name, value);
    }
    
    /** The STDOUT. */
    public static Level STDOUT = new StdOutErrLevel("STDOUT", Level.INFO.intValue()+53);
    
    /** The STDERR. */
    public static Level STDERR = new StdOutErrLevel("STDERR", Level.INFO.intValue()+54);


    /**
	 * Read resolve.
	 * 
	 * @return the object
	 * @throws ObjectStreamException
	 *             the object stream exception
	 */
    protected Object readResolve() throws ObjectStreamException {
        if (this.intValue() == STDOUT.intValue())
            return STDOUT;
        if (this.intValue() == STDERR.intValue())
            return STDERR;
        throw new InvalidObjectException("Unknown instance :" + this);
    }        
}
