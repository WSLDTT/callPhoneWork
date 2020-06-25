package com.tt.work.starter.impoetfile.model;


import java.io.Serializable;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
public class ExeclModel implements Serializable {

    private String id;

    private String date;

    private String aliWWID;

    private String explainState;

    private String phone;

    private String phone2;

    private String callTime;

    private String succeeState;

    private String name;

    private String insertDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAliWWID() {
        return aliWWID;
    }

    public void setAliWWID(String aliWWID) {
        this.aliWWID = aliWWID;
    }

    public String getExplainState() {
        return explainState;
    }

    public void setExplainState(String explainState) {
        this.explainState = explainState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSucceeState() {
        return succeeState;
    }

    public void setSucceeState(String succeeState) {
        this.succeeState = succeeState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public String toString() {
        return "ExeclModel{" +
                "date='" + date + '\'' +
                ", aliWWID='" + aliWWID + '\'' +
                ", explainState='" + explainState + '\'' +
                ", phone='" + phone + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", callTime='" + callTime + '\'' +
                ", succeeState='" + succeeState + '\'' +
                ", name='" + name + '\'' +
                ", insertDate='" + insertDate + '\'' +
                '}';
    }
}
