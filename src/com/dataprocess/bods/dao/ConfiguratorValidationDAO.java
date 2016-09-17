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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.ConfiguratorExecutionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;

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

    /**
     * Merge status for configurator.
     *
     * @param session the session
     * @param configuratorExecutionEO the configurator execution eo
     * @return the int
     * @throws BODSException the bODS exception
     */
    public int mergeStatusForConfigurator(Session session, ConfiguratorExecutionEO configuratorExecutionEO)
        throws BODSException {
        int loaderExecuitonId = 0;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            configuratorExecutionEO = (ConfiguratorExecutionEO) session.merge(configuratorExecutionEO);
            loaderExecuitonId = configuratorExecutionEO.getConfiguratorExecId();
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            throw new BODSException("ConfiguratorValidationDAO", "mergeStatusForConfigurator", exception.getMessage());
        }
        return loaderExecuitonId;
    }

    /**
     * Gets the configurator current status.
     *
     * @param configuratorExecId the configurator exec id
     * @return the configurator current status
     * @throws BODSException the bODS exception
     */
    public ConfiguratorExecutionEO getConfiguratorCurrentStatus(int configuratorExecId) throws BODSException {
        Session session = null;
        Criteria criteria = null;
        ConfiguratorExecutionEO configuratorExecutionEO = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
            criteria = session.createCriteria(ConfiguratorExecutionEO.class);
            criteria.add(Restrictions.eq("configuratorExecId", configuratorExecId));
            configuratorExecutionEO = (ConfiguratorExecutionEO) criteria.uniqueResult();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorValidationDAO", "getConfiguratorCurrentStatus", exception.getMessage());
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return configuratorExecutionEO;
    }
}
