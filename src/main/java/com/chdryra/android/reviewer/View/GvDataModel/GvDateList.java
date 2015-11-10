/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDateList extends GvDataList<GvDateList.GvDate> {
    public static final Parcelable.Creator<GvDateList> CREATOR = new Parcelable
            .Creator<GvDateList>() {
        //Overridden
        public GvDateList createFromParcel(Parcel in) {
            return new GvDateList(in);
        }

        public GvDateList[] newArray(int size) {
            return new GvDateList[size];
        }
    };

    //Constructors
    public GvDateList() {
        super(GvDate.TYPE, null);
    }

    public GvDateList(Parcel in) {
        super(in);
    }

    public GvDateList(GvReviewId id) {
        super(GvDate.TYPE, id);
    }

    public GvDateList(GvDateList data) {
        super(data);
    }

//Classes

    /**
     * {@link } version of: {@link java.util.Date}
     * {@link ViewHolder}: {@link VhDate}
     * <p/>
     * <p>
     * Ignores case when comparing dates.
     * </p>
     */
    public static class GvDate extends GvDataBasic<GvDate> implements DataDate{
        public static final GvDataType<GvDate> TYPE = new GvDataType<>(GvDate.class, "date");
        public static final Parcelable.Creator<GvDate> CREATOR = new Parcelable
                .Creator<GvDate>() {
            //Overridden
            public GvDate createFromParcel(Parcel in) {
                return new GvDate(in);
            }

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
            return isValidForDisplay();
        }
    }
}
