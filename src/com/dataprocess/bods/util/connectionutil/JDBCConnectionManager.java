package com.dataprocess.bods.util.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public final class JDBCConnectionManager {

	private Connection connection;

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean getJDBCConnection() {
		boolean connectionFlag = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.254:1521:ORCL", "snipers", "snipers");
			
			if (connection != null) {
				connectionFlag = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return connectionFlag;
	}

	public void closeConnection(Connection connection, Statement statement,
			ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if ((connection != null) && (!(connection.isClosed()))) {
				connection.close();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}
