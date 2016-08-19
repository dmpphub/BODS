package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.vo.InstanceDetailsVO;

public class InstanceDetailsDAO {

	public boolean saveInstanceDetails(InstanceDetailsVO instanceDetailsVO) {
		JDBCConnectionManager connectionManager = null;
		Connection connection = null;
		PreparedStatement ps = null;
		StringBuffer queryBuffer = null;
		int parameterIndex = 0;
		boolean hasCompleted = false;

		try {
			connectionManager = new JDBCConnectionManager();
			queryBuffer = new StringBuffer();
			if (connectionManager.getJDBCConnection()) {
				connection = connectionManager.getConnection();

				queryBuffer.append("INSERT");
				queryBuffer.append("  INTO");
				queryBuffer.append("    BODS_CONNECTION_CFG");
				queryBuffer.append("    (");
				queryBuffer.append("      CONNECTION_ID,");
				queryBuffer.append("      CONNECTION_NAME,");
				queryBuffer.append("      DISPLAY_NAME,");
				queryBuffer.append("      DESCRIPTION,");
				queryBuffer.append("      DB_USERNAME,");
				queryBuffer.append("      DB_PASSWORD,");
				queryBuffer.append("      DB_PORT,");
				queryBuffer.append("      DB_SERVICE_ID,");
				queryBuffer.append("      CONNECTION_MODE_TYPE");
				queryBuffer.append("    )");
				queryBuffer.append("    VALUES");
				queryBuffer.append("    (");
				queryBuffer.append(" BODS_CONNECTION_CFG_S.NEXTVAL,");
				queryBuffer.append("?,?,?,?,?,?,?,?)");
				ps = connection.prepareStatement(queryBuffer.toString());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getInstanceName());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getDisplayName());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getDescription());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getDbUserName());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getDbPassword());
				ps.setString(++parameterIndex, instanceDetailsVO.getPort());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getServiceName());
				ps.setString(++parameterIndex,
						instanceDetailsVO.getInstanceType());
				if (ps.executeUpdate() > 0) {
					hasCompleted = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeConnection(connection, ps, null);
		}
		return hasCompleted;
	}
}
