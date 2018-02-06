/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhDate;

/**
 * {@link } version of: {@link java.util.Date}
 * {@link ViewHolder}: {@link VhDate}
 * <p/>
 * <p>
 * Ignores case when comparing dates.
 * </p>
 */
public class GvDate extends GvDataParcelableBasic<GvDate> implements DataDate {
    public static final GvDataType<GvDate> TYPE = new GvDataType<>(GvDate.class, TYPE_NAME);

    public static final Creator<GvDate> CREATOR = new Creator<GvDate>() {
        @Override
        public GvDate createFromParcel(Parcel in) {
            return new GvDate(in);
        }

        @Override
        public GvDate[] newArray(int size) {
            return new GvDate[size];
        }
    };

    private final long mTime;

    //Constructors
    public GvDate() {
        this(null, 0);
    }

    public GvDate(long time) {
        this(null, time);
    }

    public GvDate(@Nullable GvReviewId id, long time) {
        super(GvDate.TYPE, id);
        mTime = time;
    }

    public GvDate(GvDate date) {
        this(date.getGvReviewId(), date.getTime());
    }

    GvDate(Parcel in) {
        super(in);
        mTime = in.readLong();
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(mTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDate)) return false;
        if (!super.equals(o)) return false;

        GvDate gvDate = (GvDate) o;

        return mTime == gvDate.mTime;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDate();
    }

    @Override
    public boolean isValidForDisplay() {
        return mTime != 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    public static class Reference extends GvDataRef<Reference, DataDate, VhDate> {
        public static final GvDataType<GvDate.Reference> TYPE
                = new GvDataType<>(GvDate.Reference.class, GvDate.TYPE);

        public Reference(ReviewItemReference<DataDate> reference,
                         DataConverter<DataDate, GvDate, ?> converter) {
            super(TYPE, reference, converter, VhDate.class, new PlaceHolderFactory<DataDate>() {
                @Override
                public DataDate newPlaceHolder(String placeHolder) {
                    return new GvDate(0);
                }
            });
        }
    }
}
