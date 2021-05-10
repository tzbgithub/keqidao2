package com.qidao.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("idCard")
    private String idCard;

    @JsonProperty("addTime")
    private Date addTime;

    @JsonProperty("editTime")
    private Date editTime;

    @JsonProperty("employeeNumber")
    private String employeeNumber;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("deleted")
    private Boolean deleted;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonIgnore
    public String getIdCard() {
        return idCard;
    }

    @JsonIgnore
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    @JsonIgnore
    public Date getAddTime() {
        return addTime;
    }

    @JsonIgnore
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @JsonIgnore
    public Date getEditTime() {
        return editTime;
    }

    @JsonIgnore
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    @JsonIgnore
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @JsonIgnore
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber == null ? null : employeeNumber.trim();
    }

    @JsonIgnore
    public Integer getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return deleted;
    }

    @JsonIgnore
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
