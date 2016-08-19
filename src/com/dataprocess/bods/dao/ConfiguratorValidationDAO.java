/*
 * 
 */
package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dataprocess.bods.util.BODSException;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguratorValidationDAO.
 */
public final class ConfiguratorValidationDAO {

    /**
     * Parses the query.
     * 
     * @param connection the connection
     * @param referenceType the reference type
     * @param prevalidationQuery the prevalidation query
     * @return the list
     * @throws BODSException the bODS exception
     * @throws SQLException the sQL exception
     */
    public List<String> parseQuery(Connection connection, String referenceType, String prevalidationQuery)
        throws BODSException, SQLException {
        String tableColumnQuery = "";
        List<String> referenceColumnNameList = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            referenceColumnNameList = new ArrayList<String>();
            if ("Query".equals(referenceType)) {
                preparedStatement = connection.prepareStatement(prevalidationQuery);
                resultSet = preparedStatement.executeQuery();
                resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    referenceColumnNameList.add(resultSetMetaData.getColumnName(i));
                }
            } else {
                if ("Table".equals(referenceType)) {
                    tableColumnQuery = "SELECT * FROM " + prevalidationQuery + " WHERE 1 = 2 ";
                    preparedStatement = connection.prepareStatement(tableColumnQuery);
                    resultSet = preparedStatement.executeQuery();
                    resultSetMetaData = resultSet.getMetaData();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        referenceColumnNameList.add(resultSetMetaData.getColumnName(i));
                    }
                }
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorValidationDAO", "parseQuery", exception.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException sqlException) {
                throw new BODSException("ConfiguratorValidationDAO", "parseQuery", sqlException.getMessage());
            }
        }
        return referenceColumnNameList;
    }

}
