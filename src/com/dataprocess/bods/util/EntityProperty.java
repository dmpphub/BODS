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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface EntityColumn.
 */
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityProperty {

	/**
	 * Column name.
	 * 
	 * @return the string
	 */
	public String columnName() default "";
	
	/**
	 * Entity.
	 * 
	 * @return the string
	 */
	public String entity() default "";
	
	/**
	 * Type.
	 * 
	 * @return the string
	 */
	public String type() default "";
}
