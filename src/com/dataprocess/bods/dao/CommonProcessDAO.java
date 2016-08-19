package com.dataprocess.bods.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.ConnectionConfigurationEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;

/**
 * The Class CommonProcessDAO.
 */
public final class CommonProcessDAO {

    /**
     * Gets the source configurator.
     * 
     * @param configuratorConnectionId the configurator connection id
     * @return the source configurator
     * @throws BODSException the bODS exception
     */
    public ConnectionConfigurationEO getSourceConfigurator(int configuratorConnectionId) throws BODSException {
        Session session = null;
        Criteria criteria = null;
        ConnectionConfigurationEO connectionConfigurationEO = null;
        try {
            session = HibernateSessionManager.getHibernateSession();
			criteria = session.createCriteria(ConnectionConfigurationEO.class);
			criteria.add(Restrictions.eq("connectionId", configuratorConnectionId));
			connectionConfigurationEO = (ConnectionConfigurationEO) criteria.uniqueResult();
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getSourceConfigurator", exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return connectionConfigurationEO;
    }

}
