package com.bearya.robot.fairystory.ui.station;


import android.os.Parcel;
import android.os.Parcelable;

import com.bearya.robot.base.play.FaceType;

public class LibItem implements Parcelable {
    private String name;
    private String type;
    private String image;
    private String mp3;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.image);
        dest.writeString(this.mp3);
    }

    public LibItem() {
    }

    protected LibItem(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.image = in.readString();
        this.mp3 = in.readString();
    }

    public static final Creator<LibItem> CREATOR = new Creator<>() {
        @Override
        public LibItem createFromParcel(Parcel source) {
            return new LibItem(source);
        }

        @Override
        public LibItem[] newArray(int size) {
            return new LibItem[size];
        }
    };

    public FaceType getFaceType() {
        if("lottie".equals(type)){
            return FaceType.Lottie;
        }
        return FaceType.Image;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getMp3() {
        return mp3;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }


}
