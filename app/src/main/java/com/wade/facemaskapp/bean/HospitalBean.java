package com.wade.facemaskapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wade on 2020/3/14.
 */
public class HospitalBean implements Parcelable {

    public static final String HOSPITAL_BEAN = "HOSPITAL_BEAN";
    private static final String HOSPITAL_ID = "醫事機構代碼";
    private static final String HOSPITAL_NAME = "醫事機構名稱";
    private static final String HOSPITAL_SALES_TYPE = "業務組別";
    private static final String HOSPITAL_SPECIAL_TYPE = "特約類別";
    private static final String HOSPITAL_YEAR = "看診年度";
    private static final String HOSPITAL_WEEK = "看診星期";
    private static final String HOSPITAL_REMARK = "看診備註";
    private static final String HOSPITAL_STATE = "開業狀況";
    private static final String HOSPITAL_DATETIME = "資料集更新時間";

    @SerializedName(HOSPITAL_ID)
    private String id;
    @SerializedName(HOSPITAL_NAME)
    private String name;
    @SerializedName(HOSPITAL_SALES_TYPE)
    private String salesType;
    @SerializedName(HOSPITAL_SPECIAL_TYPE)
    private String specialType;
    @SerializedName(HOSPITAL_YEAR)
    private String year;
    @SerializedName(HOSPITAL_WEEK)
    private String week;
    @SerializedName(HOSPITAL_REMARK)
    private String remark;
    @SerializedName(HOSPITAL_STATE)
    private String state;
    @SerializedName(HOSPITAL_DATETIME)
    private String datetime;

    public HospitalBean(String id, String name, String salesType, String specialType, String year, String week, String remark, String state, String datetime) {
        this.id = id;
        this.name = name;
        this.salesType = salesType;
        this.specialType = specialType;
        this.year = year;
        this.week = week;
        this.remark = remark;
        this.state = state;
        this.datetime = datetime;
    }

    protected HospitalBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        salesType = in.readString();
        specialType = in.readString();
        year = in.readString();
        week = in.readString();
        remark = in.readString();
        state = in.readString();
        datetime = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static final Creator<HospitalBean> CREATOR = new Creator<HospitalBean>() {
        @Override
        public HospitalBean createFromParcel(Parcel in) {
            return new HospitalBean(in);
        }

        @Override
        public HospitalBean[] newArray(int size) {
            return new HospitalBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(salesType);
        parcel.writeString(specialType);
        parcel.writeString(year);
        parcel.writeString(week);
        parcel.writeString(remark);
        parcel.writeString(state);
        parcel.writeString(datetime);
    }

    @Override
    public String toString() {
        return "HospitalBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", salesType='" + salesType + '\'' +
                ", specialType='" + specialType + '\'' +
                ", year='" + year + '\'' +
                ", week='" + week + '\'' +
                ", remark='" + remark + '\'' +
                ", state='" + state + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
