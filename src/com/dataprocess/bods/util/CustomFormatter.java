package com.dataprocess.bods.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
	@Override
	public String format(final LogRecord r) {
		StringBuilder sb = new StringBuilder();
		sb.append(formatMessage(r));
		if (null != r.getThrown()) {
			Throwable t = r.getThrown();
			PrintWriter pw = null;
			try {
				StringWriter sw = new StringWriter();
				pw = new PrintWriter(sw);
				t.printStackTrace(pw);
				sb.append(sw.toString());
			} finally {
				if (pw != null) {
					try {
						pw.close();
					} catch (Exception e) {
						
					}
				}
			}
		}
		return sb.toString();
	}
}
