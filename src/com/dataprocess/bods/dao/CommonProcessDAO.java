package com.dataprocess.bods.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dataprocess.bods.entity.ConnectionConfigurationEO;
import com.dataprocess.bods.util.BODSException;
import com.dataprocess.bods.util.connectionutil.HibernateSessionManager;
import com.dataprocess.bods.util.connectionutil.JDBCConnectionManager;
import com.dataprocess.bods.vo.ConfiguratorColumnDefinitionVO;
import com.dataprocess.bods.vo.ConfiguratorVO;

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

    /**
     * Gets the success file.
     *
     * @param stagingTableName the staging table name
     * @param configuratorVO the configurator vo
     * @return the success file
     * @throws BODSException the bODS exception
     */
    public String getSuccessFile(String stagingTableName, ConfiguratorVO configuratorVO) throws BODSException {
        int index = 0;
        int arrIndex = 0;
        int mapKeyCount = 0;
        String chartValueStr = "";
        String datatype = null;
        StringBuffer query = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] obj = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        List<String> columnHeaderList = null;
        Map<String, Object[]> successHeader = null;
        Map<String, String> columnNameWithDataType = null;
        try {
            columnHeaderList = new ArrayList<String>();
            successHeader = new TreeMap<String, Object[]>();
            columnNameWithDataType = new HashMap<String, String>();
            chartValueStr =
                formationHeaderList(configuratorVO, successHeader, columnHeaderList, columnNameWithDataType);
            obj = new Object[columnHeaderList.size()];
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                query = new StringBuffer();
                query.append(" SELECT " + chartValueStr);
                query.append(" FROM  " + stagingTableName + " STG WHERE ");
                query.append(" STATUS_CODE = 'S' ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(query.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    for (String columnName : columnHeaderList) {
                        datatype = columnNameWithDataType.get(columnName);
                        if ("String".equalsIgnoreCase(datatype)) {
                            obj[arrIndex] = rs.getString(columnName);
                        }
                        arrIndex++;
                    }
                    index++;
                    mapKeyCount = index + 1;
                    successHeader.put(String.valueOf(mapKeyCount), obj);
                }
            }
            callWritingFileMethod(configuratorVO.getConfiguratorName(), "SUCCESS", successHeader);
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getStagintTableDetailForPieChart", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return chartValueStr;
    }

    /**
     * Gets the error file.
     *
     * @param stagingTableName the staging table name
     * @param configuratorVO the configurator vo
     * @return the error file
     * @throws BODSException the bODS exception
     */
    public String getErrorFile(String stagingTableName, ConfiguratorVO configuratorVO) throws BODSException {
        int index = 0;
        int arrIndex = 0;
        int mapKeyCount = 0;
        String chartValueStr = "";
        String datatype = null;
        StringBuffer query = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] obj = null;
        JDBCConnectionManager jdbcConnectionManager = null;
        List<String> columnHeaderList = null;
        Map<String, Object[]> successHeader = null;
        Map<String, String> columnNameWithDataType = null;
        try {
            columnHeaderList = new ArrayList<String>();
            successHeader = new TreeMap<String, Object[]>();
            columnNameWithDataType = new HashMap<String, String>();
            chartValueStr =
                formationHeaderList(configuratorVO, successHeader, columnHeaderList, columnNameWithDataType);
            obj = new Object[columnHeaderList.size()];
            jdbcConnectionManager = new JDBCConnectionManager();
            if (jdbcConnectionManager.getJDBCConnection()) {
                query = new StringBuffer();
                query.append(" SELECT " + chartValueStr);
                query.append(" FROM  " + stagingTableName + " STG WHERE ");
                query.append(" STATUS_CODE = 'E' ");
                connection = jdbcConnectionManager.getConnection();
                ps = connection.prepareStatement(query.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    for (String columnName : columnHeaderList) {
                        datatype = columnNameWithDataType.get(columnName);
                        if ("String".equalsIgnoreCase(datatype)) {
                            obj[arrIndex] = rs.getString(columnName);
                        }
                        arrIndex++;
                    }
                    index++;
                    mapKeyCount = index + 1;
                    successHeader.put(String.valueOf(mapKeyCount), obj);
                }
            }
            callWritingFileMethod(configuratorVO.getConfiguratorName(), "ERROR", successHeader);
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getErrorFile", exception.getMessage());
        } finally {
            jdbcConnectionManager.closeConnection(connection, ps, rs);
        }
        return chartValueStr;
    }

    /**
     * Formation header list.
     *
     * @param configuratorVO the configurator vo
     * @param successHeader the success header
     * @param columnHeaderList the column header list
     * @param columnNameWithDataType the column name with data type
     * @return the string
     * @throws BODSException the bODS exception
     */
    private String formationHeaderList(ConfiguratorVO configuratorVO, Map<String, Object[]> successHeader,
        List<String> columnHeaderList, Map<String, String> columnNameWithDataType) throws BODSException {
        int index = 0;
        String columnHeader = "";
        Object[] obj = null;
        try {
            obj = new Object[configuratorVO.getConfiguratorColumnDefinitionVOList().size()];
            for (ConfiguratorColumnDefinitionVO configuratorColumnDefinitionVO : configuratorVO
                .getConfiguratorColumnDefinitionVOList()) {
                if (index == 0) {
                    columnHeader = configuratorColumnDefinitionVO.getColumnName();
                } else {
                    columnHeader+= ", " + configuratorColumnDefinitionVO.getColumnName();
                }
                obj[index] = configuratorColumnDefinitionVO.getColumnName();
                columnHeaderList.add(configuratorColumnDefinitionVO.getColumnName());
                columnNameWithDataType.put(configuratorColumnDefinitionVO.getColumnName(), configuratorColumnDefinitionVO.getDataType());
                index++;
            }
            successHeader.put("1", obj);
        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "getStagintTableDetailForPieChart", exception.getMessage());
        }
        return columnHeader;
    }

    /**
     * Call writing file method.
     *
     * @param configuratorName the configurator name
     * @param fileDisplayMsg the file display msg
     * @param successHeader the success header
     * @throws BODSException the bODS exception
     */
    public void callWritingFileMethod(String configuratorName, String fileDisplayMsg,
        Map<String, Object[]> successHeader) throws BODSException {
        int rownum = 0;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        String workingdirectory = "D:\\fileOutput";
        try {
            // Blank workbook
            workbook = new XSSFWorkbook();
            // Create a blank sheet
            sheet = workbook.createSheet(configuratorName + "_" + fileDisplayMsg);

            // Iterate over data and write to sheet
            Set<String> keyset = successHeader.keySet();

            for (String key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = successHeader.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String)
                        cell.setCellValue((String) obj);
                    else if (obj instanceof Integer)
                        cell.setCellValue((Integer) obj);
                }
            }

            if(!new File(workingdirectory).exists()) {
                new File(workingdirectory).mkdir();
            }
            // Write the workbook in file system
            FileOutputStream out =
                new FileOutputStream(new File(workingdirectory + "\\" + configuratorName + "_" + fileDisplayMsg
                    + ".xlsx"));
            workbook.write(out);
            out.close();

        } catch (Exception exception) {
            throw new BODSException("ConfiguratorDAO", "callWritingFileMethod", exception.getMessage());
        }
    }
}
