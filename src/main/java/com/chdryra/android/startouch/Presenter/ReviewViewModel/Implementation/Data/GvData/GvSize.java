/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterSizes;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhSize;


/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSize extends GvDualText implements DataSize {
    public static final GvDataType<GvSize> TYPE = new GvDataType<>(GvSize.class, TYPE_NAME);

    public static final Creator<GvSize> CREATOR = new Creator<GvSize>() {
        @Override
        public GvSize createFromParcel(Parcel in) {
            return new GvSize(in);
        }

        @Override
        public GvSize[] newArray(int size) {
            return new GvSize[size];
        }
    };

    private static final String PLACEHOLDER = "--";

    private int mSize;
    private GvDataType<?> mType;

    public GvSize(GvReviewId id, GvDataType<?> type, int size) {
        super(id, String.valueOf(size), size == 1 ? type.getDatumName() : type.getDataName());
        mType = type;
        mSize = size;
    }

    private GvSize() {
    }

    private GvSize(GvReviewId id, GvDataType<?> type) {
        super(id, PLACEHOLDER, type.getDataName());
        mType = type;
        mSize = 0;
    }

    private GvSize(Parcel in) {
        super(in);
    }

    public GvDataType<?> getType() {
        return mType;
    }

    public String getPlaceholder() {
        return PLACEHOLDER;
    }

    public boolean hasSize() {
        return !getUpper().equals(PLACEHOLDER);
    }

    @Override
    public int getSize() {
        return mSize;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhSize();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mSize);
        parcel.writeParcelable(mType, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvSize)) return false;
        if (!super.equals(o)) return false;

        GvSize that = (GvSize) o;

        return mSize == that.mSize && mType.equals(that.mType);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mSize;
        result = 31 * result + mType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    public static class Reference extends GvDataRef<Reference, DataSize, VhSize> {
        public static final GvDataType<Reference> TYPE
                = new GvDataType<>(Reference.class, GvSize.TYPE);

        private final GvDataType<?> mSizedType;

        public Reference(ReviewItemReference<DataSize> reference, GvConverterSizes converter) {
            super(TYPE, reference, converter, VhSize.class, new Factory());
            mSizedType = converter.getType();
            ((Factory) getPlaceholderFactory()).setType(mSizedType);
        }

        public GvDataType<?> getSizedType() {
            return mSizedType;
        }

        @Override
        protected VhDataReference<DataSize> newViewHolder() {
            return new VhSize.Reference(new Factory(), new GvConverterSizes(TYPE));
        }

        public static class Factory implements PlaceHolderFactory<DataSize> {
            private GvDataType<?> mSizedType;

            public void setType(GvDataType<?> sizedType) {
                mSizedType = sizedType;
            }

            @Override
            public DataSize newPlaceHolder(String placeHolder) {
                return new GvSize(new GvReviewId(), mSizedType);
            }
        }
    }
}
