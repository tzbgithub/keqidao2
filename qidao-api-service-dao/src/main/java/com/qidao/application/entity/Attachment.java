package com.qidao.application.entity;

import java.util.Date;

public class Attachment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.file_name
     *
     * @mbggenerated
     */
    private String fileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.storage_path
     *
     * @mbggenerated
     */
    private String storagePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.file_size
     *
     * @mbggenerated
     */
    private Long fileSize;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.ext_name
     *
     * @mbggenerated
     */
    private String extName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attachment.deleted
     *
     * @mbggenerated
     */
    private Boolean deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.id
     *
     * @return the value of attachment.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.id
     *
     * @param id the value for attachment.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.file_name
     *
     * @return the value of attachment.file_name
     *
     * @mbggenerated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.file_name
     *
     * @param fileName the value for attachment.file_name
     *
     * @mbggenerated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.storage_path
     *
     * @return the value of attachment.storage_path
     *
     * @mbggenerated
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.storage_path
     *
     * @param storagePath the value for attachment.storage_path
     *
     * @mbggenerated
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath == null ? null : storagePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.file_size
     *
     * @return the value of attachment.file_size
     *
     * @mbggenerated
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.file_size
     *
     * @param fileSize the value for attachment.file_size
     *
     * @mbggenerated
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.ext_name
     *
     * @return the value of attachment.ext_name
     *
     * @mbggenerated
     */
    public String getExtName() {
        return extName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.ext_name
     *
     * @param extName the value for attachment.ext_name
     *
     * @mbggenerated
     */
    public void setExtName(String extName) {
        this.extName = extName == null ? null : extName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.add_time
     *
     * @return the value of attachment.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.add_time
     *
     * @param addTime the value for attachment.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attachment.deleted
     *
     * @return the value of attachment.deleted
     *
     * @mbggenerated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attachment.deleted
     *
     * @param deleted the value for attachment.deleted
     *
     * @mbggenerated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}