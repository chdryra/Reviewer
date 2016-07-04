/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataSize;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataSize extends GvDualText implements DataSize{
    public static final GvDataType<GvDataSize> TYPE = new GvDataType<>(GvDataSize.class, "size");
    public static final Creator<GvDataSize> CREATOR = new Creator<GvDataSize>() {
        @Override
        public GvDataSize createFromParcel(Parcel in) {
            return new GvDataSize(in);
        }

        @Override
        public GvDataSize[] newArray(int size) {
            return new GvDataSize[size];
        }
    };

    private static final String PLACEHOLDER = "--";

    private int mSize;
    private GvDataType<?> mType;

    public GvDataSize() {
    }

    public GvDataSize(GvReviewId id, GvDataType<?> type, int size) {
        super(id, String.valueOf(size), size == 1 ? type.getDatumName() : type.getDataName());
        mType = type;
        mSize = size;
    }

    public GvDataSize(GvReviewId id, GvDataType<?> type) {
        super(id, PLACEHOLDER, type.getDataName());
        mType = type;
        mSize = 0;
    }

    public GvDataSize(Parcel in) {
        super(in);
    }

    public GvDataType<?> getType() {
        return mType;
    }

    @Override
    public int getSize() {
        return mSize;
    }

    public String getPlaceholder() {
        return PLACEHOLDER;
    }

    public boolean hasSize() {
        return !getUpper().equals(PLACEHOLDER);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDataSize();
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mSize);
        parcel.writeParcelable(mType, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataSize)) return false;
        if (!super.equals(o)) return false;

        GvDataSize that = (GvDataSize) o;

        if (mSize != that.mSize) return false;
        return mType.equals(that.mType);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mSize;
        result = 31 * result + mType.hashCode();
        return result;
    }
}
