package com.ceiec.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <b>修改记录：</b>
 * <p>
 * <li>
 * <p>
 * ---- 李涛 2017年9月1日
 * </li>
 * </p>
 * <p>
 * <b>类说明：</b>
 * <p>
 * 绩效考评记录
 * </p>
 */
@Entity
@Table(name = "KPIRECORD")
public class KpiRecord implements Serializable {


    private static final long serialVersionUID = -6900085851256564789L;

    /**
     * 主键，考评记录ID
     */
    private String kpiRecordId;
    /**
     * 被考评人
     */
    private String userId;
    /**
     * 被考评人角色编码
     */
    private Integer roleCode;
    /**
     * 考评开始时间
     */
    private String startTime;
    /**
     * 考评结束时间
     */
    private String endTime;
    /**
     * 考评总分
     */
    private Double kpiValue;
    /**
     * 评价内容
     */
    private String kpiComment;
    /**
     * 考评主管
     */
    private String superiors;
    /**
     * 生成考评记录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date kpiDate;
    /**
     * 考评者Id
     */
    private String evaluatorId;

    @Id
    public String getKpiRecordId() {
        return this.kpiRecordId;
    }

    public void setKpiRecordId(String kpiRecordId) {
        this.kpiRecordId = kpiRecordId;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Integer getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }


    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Double getKpiValue() {
        return this.kpiValue;
    }

    public void setKpiValue(Double kpiValue) {
        this.kpiValue = kpiValue;
    }


    public String getKpiComment() {
        return this.kpiComment;
    }

    public void setKpiComment(String kpiComment) {
        this.kpiComment = kpiComment;
    }


    public String getSuperiors() {
        return this.superiors;
    }

    public void setSuperiors(String superiors) {
        this.superiors = superiors;
    }


    public Date getKpiDate() {
        return this.kpiDate;
    }

    public void setKpiDate(Date kpiDate) {
        this.kpiDate = kpiDate;
    }


    public String getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(String evaluatorId) {
        this.evaluatorId = evaluatorId;
    }


}