/*
 * Copyright(c) 2011 Chain-Sys Corporation Inc.
 * Duplication or distribution of this code in part or in whole by any media
 * without the express written permission of Chain-Sys Corporation or its agents is
 * strictly prohibited.
 * 
 * REVISION		   DATE			NAME	 DESCRIPTION
 * 511.101		01-FEB-2012		MDR		 Initial Code 	
 * 511.102      31-JUL-2013     RAJA     Code revamp
 * 511.103      17-OCT-2013     RAJESH   Null check in populateToEntityObject()
 * 
 */
package com.dataprocess.bods.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Class SpringBeanUtils.
 */
public final class SpringBeanUtils implements NonObfuscator {

    /**
     * Populate to entity object.
     * 
     * @param dtoObject the dto object
     * @param entityObject the entity object
     * @return the object
     * @throws Exception the exception
     */
    public Object populateToEntityObject(Object dtoObject, Object entityObject) throws Exception {
        Field fieldArr[] = dtoObject.getClass().getDeclaredFields();
        Object dObject = null, eObject = null;
        AppReflection appRef = new AppReflection();
        for (Field field : fieldArr) {
            if (field.isAnnotationPresent(EntityProperty.class)) {
                EntityProperty property = field.getAnnotation(EntityProperty.class);
                String entityMethodName =
                    "set" + property.columnName().substring(0, 1).toUpperCase() + property.columnName().substring(1);
                String dtoMethodName =
                    "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                /*
                 * System.out.println(field.getName() + " - " + entityMethodName + " - " + dtoMethodName);
                 */
                String entityName = property.entity();
                if (entityName != null && !entityName.trim().equals("")) {
                    dObject = appRef.invokeMethod(dtoObject, dtoMethodName, null);
                    if (field.getType().getName().equals("java.util.List")) {
                        List<?> dObjList = (List<?>) dObject;
                        List<Object> eObjList = new ArrayList<Object>();
                        if (dObjList != null) {
                            for (Object dObj : dObjList) {
                                Object eObj = Class.forName(entityName).newInstance();
                                if (dObj != null) {
                                    populateToEntityObject(dObj, eObj);
                                    eObjList.add(eObj);
                                }
                            }
                        }
                        eObject = eObjList;
                    } else if (field.getType().getName().equals("java.util.Set")) {
                        Set<?> dObjSet = (Set<?>) dObject;
                        Set<Object> eObjSet = new HashSet<Object>();
                        if (dObjSet != null) {
                            Iterator<?> iter = dObjSet.iterator();
                            while (iter.hasNext()) {
                                Object dObj = iter.next();
                                Object eObj = Class.forName(entityName).newInstance();
                                if (dObj != null) {
                                    populateToEntityObject(dObj, eObj);
                                    eObjSet.add(eObj);
                                }
                            }
                        }
                        eObject = eObjSet;
                    } else {
                        if (dObject != null) {
                            eObject = Class.forName(entityName).newInstance();
                            populateToEntityObject(dObject, eObject);
                        }
                    }
                } else {
                	
                	
                    eObject = appRef.invokeMethod(dtoObject, dtoMethodName, null);
                }
                /*
                 * System.out.println(field.getName() + " - " + entityMethodName + " - " + dtoMethodName);
                 */
                appRef.invokeMethod(entityObject, entityMethodName, new Object[] {eObject});
            }
        }
        return entityObject;
    }

    /**
     * Populate to dto object.
     * 
     * @param entityObject the entity object
     * @param dtoObject the dto object
     * @return the object
     * @throws Exception the exception
     */
    public Object populateToDTOObject(Object entityObject, Object dtoObject) throws Exception {
        Field fieldArr[] = dtoObject.getClass().getDeclaredFields();
        Object dObject = null, eObject = null;
        AppReflection appRef = new AppReflection();
        for (Field field : fieldArr) {
            if (field.isAnnotationPresent(EntityProperty.class)) {
                EntityProperty property = field.getAnnotation(EntityProperty.class);
                String entityMethodName =
                    "get" + property.columnName().substring(0, 1).toUpperCase() + property.columnName().substring(1);
                String dtoMethodName =
                    "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                String entityName = property.entity();
                if (entityName != null && !entityName.trim().equals("")) {
                    eObject = appRef.invokeMethod(entityObject, entityMethodName, null);
                    if (field.getType().getName().equals("java.util.List")) {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) type;
                            String typeClass = pt.getActualTypeArguments()[0].toString().substring(6);
                            List<?> eObjList = (List<?>) eObject;
                            List<Object> dObjList = new ArrayList<Object>();
                            if (eObjList != null) {
                                for (Object eObj : eObjList) {
                                    Object dObj = Class.forName(typeClass).newInstance();
                                    populateToDTOObject(eObj, dObj);
                                    dObjList.add(dObj);
                                }
                            }
                            dObject = dObjList;
                        }
                    } else if (field.getType().getName().equals("java.util.Set")) {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) type;
                            String typeClass = pt.getActualTypeArguments()[0].toString().substring(6);
                            Set<?> eObjSet = (Set<?>) eObject;
                            Set<Object> dObjSet = new HashSet<Object>();
                            if (eObjSet != null) {
                                Iterator<?> iter = eObjSet.iterator();
                                while (iter.hasNext()) {
                                    Object eObj = iter.next();
                                    Object dObj = Class.forName(typeClass).newInstance();
                                    populateToDTOObject(eObj, dObj);
                                    dObjSet.add(dObj);
                                }
                            }
                            dObject = dObjSet;
                        }
                    } else {
                        dObject = field.getType().newInstance();
                        if (eObject != null && dObject != null) {
                            populateToDTOObject(eObject, dObject);
                        }
                    }
                } else {
                    dObject = appRef.invokeMethod(entityObject, entityMethodName, null);
                }
                /*
                 * System.out.println(field.getName() + " - " + entityMethodName + " - " + dtoMethodName);
                 */
                appRef.invokeMethod(dtoObject, dtoMethodName, new Object[] {dObject});
            }
        }
        return dtoObject;
    }

    /**
     * Populate entity list to dto list.
     * 
     * @param entityList the entity list
     * @param dtoList the dto list
     * @param dObject the d object
     * @return the list
     * @throws Exception the exception
     */
    public List<Object> populateEntityListToDTOList(List<Object> entityList, List<Object> dtoList, Object dObject)
        throws Exception {
        for (Object eObject : entityList) {
            dObject = dObject.getClass().newInstance();
            dObject = populateToDTOObject(eObject, dObject);
            dtoList.add(dObject);
        }
        return dtoList;
    }

    /**
     * Populate dto list to entity list.
     * 
     * @param dtoList the dto list
     * @param entityList the entity list
     * @param eObject the e object
     * @return the list
     * @throws Exception the exception
     */
    public List<Object> populateDTOListToEntityList(List<Object> dtoList, List<Object> entityList, Object eObject)
        throws Exception {
        for (Object dObject : dtoList) {
            eObject = eObject.getClass().newInstance();
            eObject = populateToEntityObject(dObject, eObject);
            entityList.add(eObject);
        }
        return entityList;
    }

    /**
     * Convert list to map.
     * 
     * @param objectList the object list
     * @param keyProperty the key property
     * @return the hash map
     */
    public HashMap<String, Object> convertListToMap(List<?> objectList, String keyProperty) {
        HashMap<String, Object> procedureMap = new HashMap<String, Object>();
        String key = "";
        String getKeyMethod =
            "get"
                + keyProperty.replaceFirst(new Character(keyProperty.charAt(0)).toString(),
                    new Character(keyProperty.charAt(0)).toString().toUpperCase());
        AppReflection appRef = new AppReflection();
        for (Object object : objectList) {
            key = (String) appRef.invokeMethod(object, getKeyMethod, null);
            procedureMap.put(key, object);
        }
        return procedureMap;
    }

    /**
     * Convert list to map.
     * 
     * @param objectList the object list
     * @param keyProperty the key property
     * @param valueProperty the value property
     * @return the hash map
     */
    public HashMap<String, Object> convertListToMap(List<?> objectList, String keyProperty, String valueProperty) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        String key = "";
        String getKeyMethod =
            "get"
                + keyProperty.replaceFirst(new Character(keyProperty.charAt(0)).toString(),
                    new Character(keyProperty.charAt(0)).toString().toUpperCase());
        String getValueMethod =
            "get"
                + valueProperty.replaceFirst(new Character(valueProperty.charAt(0)).toString(), new Character(
                    valueProperty.charAt(0)).toString().toUpperCase());
        AppReflection appRef = new AppReflection();
        for (Object object : objectList) {
            key = (String) appRef.invokeMethod(object, getKeyMethod, null);
            dataMap.put(key, appRef.invokeMethod(object, getValueMethod, null));
        }
        return dataMap;
    }

    /**
     * Populate user defined vo.
     * 
     * @param dataList the data list
     * @param dtoClass the dto class
     * @return the object[]
     * @throws Exception the exception
     */
    public Object[] populateUserDefinedVO(List<Map<String, Object>> dataList, Class<?> dtoClass) throws Exception {
        Field[] fldArr = dtoClass.getDeclaredFields();
        String keyName = "";
        Object dtoObject = null;
        String dtoMethodName = "";
        UserDefinedReflection appRef = new UserDefinedReflection();
        Object value = "";
        int mapSize = dataList.size();
        Object dObj = Array.newInstance(dtoClass, mapSize);
        int idx = 0;
        for (Map<String, Object> hMap : dataList) {
            dtoObject = dtoClass.newInstance();
            for (Field field : fldArr) {
                if (field.isAnnotationPresent(EntityProperty.class)) {
                    EntityProperty property = field.getAnnotation(EntityProperty.class);
                    keyName = property.columnName().toUpperCase();
                    if (hMap.containsKey(keyName)) {
                        value = hMap.get(keyName);
                        dtoMethodName =
                            "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        appRef.invokeMethod(dtoObject, dtoMethodName, new Object[] {value});
                    }
                }
            }
            Array.set(dObj, idx, dtoObject);
            idx++;
        }
        Object[] destObjArr = null;
        destObjArr = (Object[]) dObj;
        return destObjArr;
    }

    /**
     * Populate user defined vo as list.
     * 
     * @param dataList the data list
     * @param dtoClass the dto class
     * @return the list
     * @throws Exception the exception
     */
    public List<Object> populateUserDefinedVOAsList(List<Map<String, Object>> dataList, Class<?> dtoClass)
        throws Exception {
        Field[] fldArr = dtoClass.getDeclaredFields();
        String keyName = "";
        Object dtoObject = null;
        String dtoMethodName = "";
        List<Object> objectList = new ArrayList<Object>();
        UserDefinedReflection appRef = new UserDefinedReflection();
        Object value = "";
        for (Map<String, Object> hMap : dataList) {
            dtoObject = dtoClass.newInstance();
            for (Field field : fldArr) {
                if (field.isAnnotationPresent(EntityProperty.class)) {
                    EntityProperty property = field.getAnnotation(EntityProperty.class);
                    keyName = property.columnName().toUpperCase();
                    if (hMap.containsKey(keyName)) {
                        value = hMap.get(keyName);
                        dtoMethodName =
                            "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        appRef.invokeMethod(dtoObject, dtoMethodName, new Object[] {value});

                    }
                }
            }
            objectList.add(dtoObject);
        }
        return objectList;
    }
   

    /**
     * The main method.
     * 
     * @param ar the arguments
     * @throws Exception the exception
     */
    public static void main(String ar[]) throws Exception {
    	/*ExtractVO extractVO = new ExtractVO();
    	extractVO.setActiveFlag("Y");
    	extractVO.setExtractName("TEST-EXTRACT");
    	extractVO.setExtractDisplayName("TestExtract");
    	ExtDetail dtl = new ExtDetail();
    	new SpringBeanUtils().populateToEntityObject(extractVO, dtl); 
    	
    	System.out.println("Ext Name : " + dtl.getExtName());
    	System.out.println("Ext Name : " + dtl.getActiveFlag());
    	System.out.println("Ext Name : " + dtl.getDisplayName());*/
    	
    }
}
