/*
 * Copyright(c) 2011 Chain-Sys Corporation Inc.
 * Duplication or distribution of this code in part or in whole by any media
 * without the express written permission of Chain-Sys Corporation or its agents is
 * strictly prohibited.
 * 
 * REVISION		   DATE			NAME	 DESCRIPTION
 * 511.101		01-FEB-2012		MDR		 Initial Code 	
 */
package com.dataprocess.bods.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentSet;

/**
 * The Class AppReflection.
 */
public final class AppReflection {

	/**
	 * Invoke method.
	 * 
	 * @param o
	 *            the o
	 * @param methodName
	 *            the method name
	 * @param args
	 *            the args
	 * @return the object
	 */
	public Object invokeMethod(Object o, String methodName, Object[] args) {
		Class<?> targetClass = o.getClass();
		Class<?>[] argumentArray = null;

		if (args != null) {
			argumentArray = new Class[args.length];
			for (int len = 0; len < args.length; len++) {
				if (args[len] == null) {
					argumentArray[len] = Object.class;
				} else if (args[len] instanceof Integer) {
					argumentArray[len] = int.class;
				} else if (args[len] instanceof Double) {
					argumentArray[len] = double.class;
				} else if (args[len] instanceof Float) {
					argumentArray[len] = float.class;
				} else if (args[len] instanceof Boolean) {
					argumentArray[len] = boolean.class;
				} else if (args[len] instanceof Long) {
					argumentArray[len] = long.class;
				} else if (args[len] instanceof Character) {
					argumentArray[len] = char.class;
				} else if (args[len] instanceof Byte) {
					argumentArray[len] = byte.class;
				} else if (args[len] instanceof Short) {
					argumentArray[len] = short.class;
				} else if (args[len] instanceof Connection) {
					argumentArray[len] = Connection.class;
				} else if (args[len] instanceof List) {
					argumentArray[len] = List.class;
				} else if (args[len] instanceof PersistentBag) {
					argumentArray[len] = List.class;
				} else if (args[len] instanceof Set) {
					argumentArray[len] = Set.class;
				} else if (args[len] instanceof PersistentSet) {
					argumentArray[len] = Set.class;
				} else if (args[len] instanceof Timestamp) {
					argumentArray[len] = Timestamp.class;
				} else if (args[len] instanceof Date) {
					argumentArray[len] = Date.class;
				} else {
					argumentArray[len] = args[len].getClass();
				}
			}
		}

		try {
			Method targetMethod = targetClass.getMethod(methodName,
					argumentArray);
			return targetMethod.invoke(o, args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//System.out.println("Not Available: " + methodName);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Invoke method with throws exception.
	 * 
	 * @param o
	 *            the o
	 * @param methodName
	 *            the method name
	 * @param args
	 *            the args
	 * @return the object
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ChainsysException
	 *             the chainsys exception
	 */
	public Object invokeMethodWithThrowsException(Object o, String methodName,
			Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		Class<?> targetClass = null;
		Class<?>[] argumentArray = null;
		Method targetMethod = null;
		try {
			targetClass = o.getClass();
			if (args != null) {
				argumentArray = new Class[args.length];
				for (int len = 0; len < args.length; len++) {
					if (args[len] == null) {
						argumentArray[len] = Object.class;
					} else if (args[len] instanceof Integer) {
						argumentArray[len] = int.class;
					} else if (args[len] instanceof Double) {
						argumentArray[len] = double.class;
					} else if (args[len] instanceof Float) {
						argumentArray[len] = float.class;
					} else if (args[len] instanceof Boolean) {
						argumentArray[len] = boolean.class;
					} else if (args[len] instanceof Long) {
						argumentArray[len] = long.class;
					} else if (args[len] instanceof Character) {
						argumentArray[len] = char.class;
					} else if (args[len] instanceof Byte) {
						argumentArray[len] = byte.class;
					} else if (args[len] instanceof Short) {
						argumentArray[len] = short.class;
					} else if (args[len] instanceof Connection) {
						argumentArray[len] = Connection.class;
					}else if (args[len] instanceof Vector) {
                        argumentArray[len] = Vector.class;
                    } else if (args[len] instanceof List) {
						argumentArray[len] = List.class;
					} else if (args[len] instanceof PersistentBag) {
						argumentArray[len] = List.class;
					} else if (args[len] instanceof Set) {
						argumentArray[len] = Set.class;
					} else if (args[len] instanceof PersistentSet) {
						argumentArray[len] = Set.class;
					} else if (args[len] instanceof Timestamp) {
						argumentArray[len] = Timestamp.class;
					} else if (args[len] instanceof Date) {
						argumentArray[len] = Date.class;
					} else {
						argumentArray[len] = args[len].getClass();
					}
				}
			}
			targetMethod = targetClass.getMethod(methodName,
					argumentArray);
			return targetMethod.invoke(o, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetMethod.invoke(o, args);
	}

	/**
	 * Invoke static method.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param methodName
	 *            the method name
	 * @param args
	 *            the args
	 * @return the object
	 */
	public static Object InvokeStaticMethod(Class<?> targetClass,
			String methodName, Object[] args) {
		Class<?>[] argumentArray = null;

		if (args != null) {
			argumentArray = new Class[args.length];
			for (int len = 0; len < args.length; len++) {
				if (args[len] instanceof Integer) {
					argumentArray[len] = int.class;
				} else if (args[len] instanceof Double) {
					argumentArray[len] = double.class;
				} else if (args[len] instanceof Float) {
					argumentArray[len] = float.class;
				} else if (args[len] instanceof Boolean) {
					argumentArray[len] = boolean.class;
				} else if (args[len] instanceof Long) {
					argumentArray[len] = long.class;
				} else if (args[len] instanceof Character) {
					argumentArray[len] = char.class;
				} else if (args[len] instanceof Byte) {
					argumentArray[len] = byte.class;
				} else if (args[len] instanceof Short) {
					argumentArray[len] = short.class;
				} else if (args[len] instanceof Connection) {
					argumentArray[len] = Connection.class;
				} else {
					argumentArray[len] = args[len].getClass();
				}
			}
		}

		try {
			Method targetMethod = targetClass.getMethod(methodName,
					argumentArray);
			//System.out.println("target : " + targetMethod);
			return targetMethod.invoke(null, args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Creates the new instance of.
	 * 
	 * @param className
	 *            the class name
	 * @param paramTypes
	 *            the param types
	 * @param args
	 *            the args
	 * @return the object
	 */
	public static Object createNewInstanceOf(Class<?> className,
			Class<?>[] paramTypes, Object[] args) {
		Object obj = null;
		try {
			if (paramTypes != null) {
				Constructor<?> ct = className.getConstructor(paramTypes);
				obj = ct.newInstance(args);
			} else {
				obj = className.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}