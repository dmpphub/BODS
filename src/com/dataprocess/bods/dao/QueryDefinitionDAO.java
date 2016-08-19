package com.dataprocess.bods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.QueryDefinitionEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;
import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.vo.QueryDefinitionDataVO;
import com.dataprocess.bods.vo.QueryDefinitionLineVO;
import com.dataprocess.bods.vo.QueryDefinitionVO;

public final class QueryDefinitionDAO {

	public boolean validateQueryDefinition(QueryDefinitionVO queryDefinitionVO) {
		JDBCConnectionManager jdbcConnectionManager = null;
		QueryDefinitionLineVO queryDefinitionLineVO = null;
		QueryDefinitionDataVO queryDefinitionDataVO = null;
		HashSet<QueryDefinitionLineVO> queryDefinitionLineVOSet = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		boolean hasValidated = false;

		try {
			jdbcConnectionManager = new JDBCConnectionManager();

			if (jdbcConnectionManager.getJDBCConnection()) {
				connection = jdbcConnectionManager.getConnection();
				ps = connection.prepareStatement(queryDefinitionVO.getSqlQuery().replaceAll(";", ""),
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
						ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				metaData = rs.getMetaData();

				if (metaData != null) {
					queryDefinitionVO.setQueryDefinitionLineVOList(new ArrayList<QueryDefinitionLineVO>());

					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						queryDefinitionLineVO = new QueryDefinitionLineVO();
						queryDefinitionLineVO.setColumnName(metaData.getColumnName(i));

						if (metaData.getColumnTypeName(i).equals("VARCHAR")
								|| metaData.getColumnTypeName(i).equals("VARCHAR2")
								|| metaData.getColumnTypeName(i).equals("CHAR")) {
							queryDefinitionLineVO.setDataType("STRING");
						} else {
							queryDefinitionLineVO.setDataType(metaData.getColumnTypeName(i));
						}

						queryDefinitionVO.getQueryDefinitionLineVOList().add(queryDefinitionLineVO);
						queryDefinitionVO.getQueryDefinitionLineVOList().get(i - 1)
								.setQueryDefinitionDataVOList(new ArrayList<QueryDefinitionDataVO>());

						while (rs.next()) {
							queryDefinitionDataVO = new QueryDefinitionDataVO();

							if (metaData.getColumnTypeName(i).equals("VARCHAR")
									|| metaData.getColumnTypeName(i).equals("VARCHAR2")
									|| metaData.getColumnTypeName(i).equals("CHAR")) {
								queryDefinitionDataVO.setDataValue(rs.getString(queryDefinitionLineVO.getColumnName()));

							} else if (metaData.getColumnTypeName(i).equals("NUMBER")) {
								queryDefinitionDataVO
										.setDataValue(String.valueOf(rs.getInt(queryDefinitionLineVO.getColumnName())));
							}
							queryDefinitionVO.getQueryDefinitionLineVOList().get(i - 1).getQueryDefinitionDataVOList()
									.add(queryDefinitionDataVO);
						}
						rs.beforeFirst();
					}
				}
				if (queryDefinitionVO.getQueryDefinitionLineVOList() != null
						&& queryDefinitionVO.getQueryDefinitionLineVOList().size() > 0) {
					queryDefinitionLineVOSet = new HashSet<QueryDefinitionLineVO>(
							queryDefinitionVO.getQueryDefinitionLineVOList());
					queryDefinitionVO.setSourceConfiguratorLineVOSet(queryDefinitionLineVOSet);
				}
				hasValidated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			jdbcConnectionManager.closeConnection(connection, ps, rs);
		}
		return hasValidated;
	}

	public boolean saveQueryDefinition(QueryDefinitionEO queryDefinitionEO) {
		boolean hasCompleted = false;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateSessionManager.getHibernateSession();
			transaction = session.beginTransaction();
			session.merge(queryDefinitionEO);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();

		} finally {
			session.close();
		}
		return hasCompleted;
	}

	public ArrayList<QueryDefinitionVO> getSourceConfigConnectionList() {
		JDBCConnectionManager jdbcConnectionManager = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		QueryDefinitionVO definitionVO = null;
		ArrayList<QueryDefinitionVO> sourceConfigConnctionList = null;
		StringBuffer queryBuffer = null;

		try {
			jdbcConnectionManager = new JDBCConnectionManager();
			queryBuffer = new StringBuffer();
			sourceConfigConnctionList = new ArrayList<QueryDefinitionVO>();

			if (jdbcConnectionManager.getJDBCConnection()) {
				queryBuffer.append(" SELECT ");
				queryBuffer.append(" CONNECTION_NAME, CONNECTION_ID ");
				queryBuffer.append(" FROM ");
				queryBuffer.append(" BODS_CONNECTION_CFG ");
				/*queryBuffer.append(" WHERE ");
				queryBuffer.append(" CONNECTION_MODE_TYPE = 'SOURCE' ");*/

				connection = jdbcConnectionManager.getConnection();
				ps = connection.prepareStatement(queryBuffer.toString());
				rs = ps.executeQuery();

				while (rs.next()) {
					definitionVO = new QueryDefinitionVO();
					definitionVO.setSourceConfigConnection(rs.getString("CONNECTION_NAME"));
					definitionVO.setSourceConfigConnectionId(rs.getInt("CONNECTION_ID"));
					sourceConfigConnctionList.add(definitionVO);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcConnectionManager.closeConnection(connection, ps, rs);
		}
		return sourceConfigConnctionList;
	}

	public void fetchQueryDefinitionList(QueryDefinitionVO queryDefinitionVO) {
		JDBCConnectionManager jdbcConnectionManager = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		QueryDefinitionVO definitionVO = null;
		StringBuffer queryBuffer = null;

		try {
			jdbcConnectionManager = new JDBCConnectionManager();
			queryBuffer = new StringBuffer();
			queryDefinitionVO.setQueryDefinitionVOList(new ArrayList<QueryDefinitionVO>());

			if (jdbcConnectionManager.getJDBCConnection()) {
				queryBuffer.append(" SELECT ");
				queryBuffer.append(" BODS_CON.CONNECTION_NAME, BODS_SOURCE.SOURCE_CFG_NAME, BODS_SOURCE.SOURCE_CFG_ID ");
				queryBuffer.append(" FROM ");
				queryBuffer.append(" BODS_CONNECTION_CFG BODS_CON, BODS_SOURCE_CFG BODS_SOURCE ");
				queryBuffer.append(" WHERE ");
				queryBuffer.append(" BODS_CON.CONNECTION_ID = BODS_SOURCE.CONNECTION_ID ");

				connection = jdbcConnectionManager.getConnection();
				ps = connection.prepareStatement(queryBuffer.toString());
				rs = ps.executeQuery();

				while (rs.next()) {
					definitionVO = new QueryDefinitionVO();
					definitionVO.setSourceConfigConnection(rs.getString("CONNECTION_NAME"));
					definitionVO.setSourceConfigNameId(rs.getInt("SOURCE_CFG_ID"));
					definitionVO.setSourceConfigName(rs.getString("SOURCE_CFG_NAME"));
					queryDefinitionVO.getQueryDefinitionVOList().add(definitionVO);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcConnectionManager.closeConnection(connection, ps, rs);
		}
	}

	public QueryDefinitionEO fetchQueryDefinitionDetails(QueryDefinitionVO queryDefinitionVO) throws BODSException {
		Session session = null;
		Criteria criteria = null;
		QueryDefinitionEO queryDefinitionEO = null;
		try {
			session = HibernateSessionManager.getHibernateSession();
			criteria = session.createCriteria(QueryDefinitionEO.class);
			criteria.add(Restrictions.eq("sourceConfiguratorId", queryDefinitionVO.getSourceConfigNameId()));
			queryDefinitionEO = (QueryDefinitionEO) criteria.uniqueResult();
			
		} catch (Exception exception) {
			throw new BODSException("ConfiguratorDAO", "createStagingTable", exception.getMessage());
		} finally {
			if (session != null) {
				session.clear();
				session.close();
			}
		}
		return queryDefinitionEO;
	}
}
