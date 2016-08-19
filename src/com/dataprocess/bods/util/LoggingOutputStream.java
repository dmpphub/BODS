package com.dataprocess.bods.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class LoggingOutputStream.
 */
public final class LoggingOutputStream extends ByteArrayOutputStream {

	/** The logger. */
	private Logger logger;

	/** The level. */
	private Level level;

	/** The date str. */
	private String dateStr = "";

	/** The record. */
	private String record = "";

	/**
	 * Instantiates a new logging output stream.
	 * 
	 * @param logger
	 *            the logger
	 * @param level
	 *            the level
	 */
	public LoggingOutputStream(Logger logger, Level level) {
		super();
		this.logger = logger;
		this.level = level;
		// lineSeparator = System.getProperty("line.separator");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException {
		dateStr = new SimpleDateFormat("dd-MM-yy hh:mm:ss a")
				.format(new Date());
		synchronized (this) {
			super.flush();
			if (this.toString().trim().length() == 0)
				record = this.toString();
			else
				record = dateStr + ": " + this.toString();
			super.reset();
		}
		if (record.length() == 0) {
			return;
		}
		// logger.logp(level, "", "", record);
		logger.log(level, record);
	}
}
