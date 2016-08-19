package com.dataprocess.bods.util;



import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.dataprocess.bods.BODSConstants;

/**
 * The Class SetStdOutErrLog.
 */
public final class SetStdOutErrLog {

	/**
	 * Sets the out err log.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void setOutErrLog() throws Exception {

		setBackUp();
		LogManager logManager = LogManager.getLogManager();
		logManager.reset();

		String outPattern = BODSConstants.REAL_PATH + "/logs/etl.out";
		String errPattern = BODSConstants.REAL_PATH + "/logs/etl.err";

		Handler outFileHandler = new FileHandler(outPattern, 200000000, 100,
				true);
		outFileHandler.setFormatter(new CustomFormatter());

		Handler errFileHandler = new FileHandler(errPattern, 200000000, 100,
				true);
		errFileHandler.setFormatter(new CustomFormatter());
		Logger logger;
		LoggingOutputStream los;

		logger = Logger.getLogger("STDOUT");
		logger.addHandler(outFileHandler);
		logger.setUseParentHandlers(false);
		los = new LoggingOutputStream(logger, StdOutErrLevel.STDOUT);
		System.setOut(new PrintStream(los, true));

		logger = Logger.getLogger("STDERR");
		logger.addHandler(errFileHandler);
		logger.setUseParentHandlers(false);
		los = new LoggingOutputStream(logger, StdOutErrLevel.STDERR);
		System.setErr(new PrintStream(los, true));

		logger = Logger.getLogger("test");
		logger.info("This is a test log message");

		try {
			throw new RuntimeException("Test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the back up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private static void setBackUp() throws Exception {
		String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		File backUp = new File(BODSConstants.REAL_PATH + "/logs/" + dateStr);
		if (!backUp.exists())
			backUp.mkdir();
		File f = new File(BODSConstants.REAL_PATH + "/logs");
		File[] fileArr = f.listFiles();
		FileReader fr = null;
		for (File file : fileArr) {
			if (file.isFile()) {
				if (!file.getName().endsWith(".lck")) {
					fr = new FileReader(file);
					char[] cbuff = new char[(int) file.length()];
					fr.read(cbuff, 0, (int) file.length());
					FileWriter fw = new FileWriter(new File(backUp,
							file.getName()));
					fw.write(cbuff);
					fw.flush();
					fw.close();
					fr.close();
				}
				file.delete();
			}
		}
	}
}
