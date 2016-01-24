/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link } version of: {@link java.util.Date}
 * {@link ViewHolder}: {@link VhDate}
 * <p/>
 * <p>
 * Ignores case when comparing dates.
 * </p>
 */
public class GvDate extends GvDataBasic<GvDate> implements DataDateReview {
    public static final GvDataType<GvDate> TYPE = new GvDataType<>(GvDate.class, "date");
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

    private long mTime;

    //Constructors
    public GvDate() {
        this(null, 0);
    }

    public GvDate(long time) {
        this(null, time);
    }

    public GvDate(GvReviewId id, long time) {
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

    //Overridden

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
    public String getStringSummary() {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(new Date(mTime));
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
}
