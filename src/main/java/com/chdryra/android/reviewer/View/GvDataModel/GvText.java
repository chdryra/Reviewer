/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDString} to comply with
 * {@link GvData}
 */
public class GvText extends VHDString implements GvData {
    public static final GvDataType<GvText> TYPE = GvTypeMaker.newType(GvText.class, "text");
    public static final Parcelable.Creator<GvText> CREATOR = new Parcelable
            .Creator<GvText>() {
        public GvText createFromParcel(Parcel in) {
            return new GvText(in);
        }

        public GvText[] newArray(int size) {
            return new GvText[size];
        }
    };
    private GvReviewId mId;

    public GvText() {
        super();
    }

    public GvText(String text) {
        super(text);
    }

    public GvText(GvReviewId id, String text) {
        super(text);
        mId = id;
    }

    public GvText(Parcel in) {
        super(in.readString());
        mId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    public GvText(GvText text) {
        this(text.getReviewId(), text.get());
    }

    @Override
    public GvDataType<? extends GvText> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return get();
    }

    @Override
    public GvReviewId getReviewId() {
        return mId;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhText();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(get());
        parcel.writeParcelable(mId, i);
    }
}
