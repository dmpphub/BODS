package com.dataprocess.bods.util.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.dataprocess.bods.dao.CommonProcessDAO;
import com.dataprocess.bods.entity.ConnectionConfigurationEO;
import com.dataprocess.bods.util.BODSException;

// TODO: Auto-generated Javadoc
/**
 * The Class TargetSchemaConnection.
 */
public final class TargetSchemaConnection {

    /**
     * Gets the target schema connection.
     * 
     * @param configurationConnetionId the configuration connetion id
     * @return the target schema connection
     * @throws BODSException the bODS exception
     */
    public Connection getTargetSchemaConnection(int configurationConnetionId) throws BODSException {
        String driverInfoUrl = "";
        String[] condetailArr = null;
        Connection targetSchemaConnetion = null;
        try {
            driverInfoUrl = getConnetionDetail(configurationConnetionId);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            condetailArr = driverInfoUrl.split(",");
            targetSchemaConnetion = DriverManager.getConnection(condetailArr[0],condetailArr[1],condetailArr[2]);
        } catch (BODSException bodsException) {
            throw bodsException;
        } catch (Exception exception) {
            throw new BODSException("TargetSchemaConnection", "getTargetSchemaConnection", exception.getMessage());
        }
        return targetSchemaConnetion;
    }

    /**
     * Gets the test connection.
     *
     * @param configurationConnetionId the configuration connetion id
     * @return the test connection
     * @throws BODSException the bODS exception
     */
    public boolean getTestConnection(int configurationConnetionId) throws BODSException {
        boolean connectionStatus = false;
        String driverInfoUrl = "";
        Connection targetSchemaConnetion = null;
        try {
            driverInfoUrl = getConnetionDetail(configurationConnetionId);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            targetSchemaConnetion = DriverManager.getConnection(driverInfoUrl);
            if (targetSchemaConnetion != null) {
                connectionStatus = true;
            }
        } catch (BODSException bodsException) {
            connectionStatus = false;
            throw bodsException;
        } catch (Exception exception) {
            connectionStatus = false;
            throw new BODSException("TargetSchemaConnection", "getTargetSchemaConnection", exception.getMessage());
        } finally {
            try {
                closeConnection(targetSchemaConnetion);
            } catch (Exception exception2) {
                throw new BODSException("TargetSchemaConnection", "getTargetSchemaConnection", exception2.getMessage());
            }
        }
        return connectionStatus;
    }

    /**
     * Gets the connetion detail.
     * 
     * @param configurationConnetionId the configuration connetion id
     * @return the connetion detail
     * @throws BODSException the bODS exception
     */
    private String getConnetionDetail(int configurationConnetionId) throws BODSException {
        int portNumber = 0;
        String hostName = "";
        String userName = "";
        String password = "";
        String driverInfoUrl = "";
        String passwordInfo = "";
        String dbServiceInfo = "";
        ConnectionConfigurationEO connectionConfigurationEO = null;
        CommonProcessDAO commonProcessDAO = null;
        try {
            commonProcessDAO = new CommonProcessDAO();
            connectionConfigurationEO = commonProcessDAO.getSourceConfigurator(configurationConnetionId);
            userName = connectionConfigurationEO.getUserName();
            password = connectionConfigurationEO.getPassword();
            hostName = connectionConfigurationEO.getHostName();
            portNumber = connectionConfigurationEO.getPortNumber();
            dbServiceInfo = connectionConfigurationEO.getServiceIdorserviceName();
            driverInfoUrl = "jdbc:oracle:thin:@" + hostName + ":" + portNumber + ":" + dbServiceInfo;
            passwordInfo = ", " + userName + ", " + password;
            driverInfoUrl += passwordInfo;
        } catch (Exception exception) {
            throw new BODSException("TargetSchemaConnection", "getConnetionDetail", exception.getMessage());
        }
        return driverInfoUrl;
    }

    /**
     * Close connection.
     *
     * @param targetSchemaConnetion the target schema connetion
     * @throws BODSException the bODS exception
     */
    private void closeConnection(Connection targetSchemaConnetion) throws BODSException {
        try {
            if (targetSchemaConnetion != null) {
                targetSchemaConnetion.close();
            }
        } catch (Exception exception) {
            throw new BODSException("TargetSchemaConnection", "closeConnection", exception.getMessage());
        }
    }

    /**
     * Target schema close.
     * 
     * @param connection the connection
     * @param preparedStatement the prepared statement
     * @param resultSet the result set
     * @param resultSetMetaData the result set meta data
     * @throws BODSException the bODS exception
     */
    public void targetSchemaClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet,
        ResultSetMetaData resultSetMetaData) throws BODSException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (Exception exception) {
            throw new BODSException("TargetSchemaConnection", "targetSchemaClose", exception.getMessage());
        }
    }
}
