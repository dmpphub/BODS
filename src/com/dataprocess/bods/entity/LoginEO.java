package com.dataprocess.bods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class LoginEO.
 */
@Entity
@Table(name = "BODS_USERS")
public final class LoginEO {

    /** The etl login id. */
    @Id
    @Column(name = "USER_ID")
    private int etlLoginId;

    /** The name. */
    @Column(name = "NAME")
    private String name;

    /** The user name. */
    @Column(name = "USER_NAME")
    private String userName;

    /** The password. */
    @Column(name = "PASSWORD")
    private String password;

    /** The mail id. */
    @Column(name = "MAIL_ID")
    private String mailId;

    /**
     * Gets the etl login id.
     * 
     * @return the etl login id
     */
    public int getEtlLoginId() {
        return etlLoginId;
    }

    /**
     * Sets the etl login id.
     * 
     * @param etlLoginId the new etl login id
     */
    public void setEtlLoginId(int etlLoginId) {
        this.etlLoginId = etlLoginId;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user name.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     * 
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the mail id.
     * 
     * @return the mail id
     */
    public String getMailId() {
        return mailId;
    }

    /**
     * Sets the mail id.
     * 
     * @param mailId the new mail id
     */
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

}
