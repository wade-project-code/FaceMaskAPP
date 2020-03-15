package com.wade.facemaskapp.bean;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Wade on 2020/3/14.
 */
public class MaskBean implements Parcelable {
    public static final String MASK_BEAN = "MASK_BEAN";
    private static final String MASK_ID = "醫事機構代碼";
    private static final String MASK_NAME = "醫事機構名稱";
    private static final String MASK_ADDRESS = "醫事機構地址";
    private static final String MASK_TEL = "醫事機構電話";
    private static final String MASK_ADULT = "成人口罩剩餘數";
    private static final String MASK_CHILD = "兒童口罩剩餘數";
    private static final String MASK_DATETIME = "來源資料時間";

    @SerializedName(MASK_ID)
    private String id;
    @SerializedName(MASK_NAME)
    private String name;
    @SerializedName(MASK_ADDRESS)
    private String address;
    @SerializedName(MASK_TEL)
    private String tel;
    @SerializedName(MASK_ADULT)
    private String adultmask;
    @SerializedName(MASK_CHILD)
    private String childmask;
    @SerializedName(MASK_DATETIME)
    private String datetime;

    public MaskBean(String id, String name, String address, String tel, String adultmask, String childmask, String datetime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.adultmask = adultmask;
        this.childmask = childmask;
        this.datetime = datetime;
    }

    protected MaskBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        tel = in.readString();
        adultmask = in.readString();
        childmask = in.readString();
        datetime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeString(adultmask);
        dest.writeString(childmask);
        dest.writeString(datetime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MaskBean> CREATOR = new Creator<MaskBean>() {
        @Override
        public MaskBean createFromParcel(Parcel in) {
            return new MaskBean(in);
        }

        @Override
        public MaskBean[] newArray(int size) {
            return new MaskBean[size];
        }
    };

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdultmask() {
        return adultmask;
    }

    public void setAdultmask(String adultmask) {
        this.adultmask = adultmask;
    }

    public String getChildmask() {
        return childmask;
    }

    public void setChildmask(String childmask) {
        this.childmask = childmask;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datatime) {
        this.datetime = datatime;
    }

    @Override
    public String toString() {
        return "MaskBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", adultmask=" + adultmask +
                ", childmask=" + childmask +
                ", datetime='" + datetime + '\'' +
                '}';
    }

}
