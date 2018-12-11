/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable {
    private int id;
    private String keyword;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.keyword);
        dest.writeString(this.value);
    }

    public DictionaryModel() {
    }

    public DictionaryModel(String keyword, String value){
        this.keyword = keyword;
        this.value = value;
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.keyword = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<DictionaryModel> CREATOR = new Parcelable.Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
