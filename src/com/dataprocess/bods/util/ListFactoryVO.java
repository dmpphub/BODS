package com.dataprocess.bods.util;

import java.io.Serializable;

import org.apache.commons.collections.Factory;

/**
 * The Class ListFactoryVO.
 */
public final class ListFactoryVO implements Factory, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1297250293447146374L;

    /** The factory class. */
    Class<?> factoryClass = null;

    /**
     * Instantiates a new list factory vo.
     * 
     * @param factoryClass the factory class
     */
    public ListFactoryVO(Class<?> factoryClass) {
        this.factoryClass = factoryClass;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.collections.Factory#create()
     */
    public Object create() {
        Object factoryObj = null;
        try {
            factoryObj = factoryClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        return factoryObj;
    }
}
