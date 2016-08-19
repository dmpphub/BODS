package com.dataprocess.bods;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;

import com.dataprocess.bods.util.SetStdOutErrLog;

public class BODSStartup extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374888898197650286L;
	
	private ServletContext application = null;

	public void init(ServletConfig config) {

		application = config.getServletContext();
		BODSConstants.REAL_PATH = application.getRealPath("");
		
		try {
			HibernateSessionManager.getHibernateSession();
			System.out.println("Get the connection successfull");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem in Get the Connection");
		}
		
		createIfNotExists("logs");
		//setStream();
	}
	
	private void createIfNotExists(String dirName) {
        File dirs = new File(BODSConstants.REAL_PATH + "/" + dirName);

        if (!dirs.exists()) {
            dirs.mkdir();
            System.out.println("Directory " + dirs + " created"); 
        }
    }
	
	private void setStream() {
		
        try {
            SetStdOutErrLog.setOutErrLog();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}