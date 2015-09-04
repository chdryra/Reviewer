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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDateList extends GvDataList<GvDateList.GvDate> {
    public static final GvDataType<GvDateList> TYPE
            = GvTypeMaker.newType(GvDateList.class, GvDate.TYPE);

    public GvDateList() {
        super(TYPE, null);
    }

    public GvDateList(GvReviewId id) {
        super(TYPE, id);
    }

    public GvDateList(GvDateList data) {
        super(data);
    }

    /**
     * {@link } version of: {@link java.util.Date}
     * {@link ViewHolder}: {@link VhDate}
     * <p/>
     * <p>
     * Ignores case when comparing dates.
     * </p>
     */
    public static class GvDate extends GvDataBasic<GvDate> {
        public static final GvDataType<GvDate> TYPE = GvTypeMaker.newType(GvDate.class, "date");
        public static final Parcelable.Creator<GvDate> CREATOR = new Parcelable
                .Creator<GvDate>() {
            public GvDate createFromParcel(Parcel in) {
                return new GvDate(in);
            }

            public GvDate[] newArray(int size) {
                return new GvDate[size];
            }
        };

        private Date mDate;

        public GvDate() {
            this(null, null);
        }

        public GvDate(Date date) {
            this(null, date);
        }

        public GvDate(GvReviewId id, Date date) {
            super(GvDate.TYPE, id);
            mDate = date;
        }

        public GvDate(GvDate date) {
            this(date.getReviewId(), date.getDate());
        }

        GvDate(Parcel in) {
            super(in);
            mDate = (Date) in.readSerializable();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeSerializable(mDate);
        }

        @Override
        public String getStringSummary() {
            DateFormat format = SimpleDateFormat.getDateInstance();
            return format.format(mDate);
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhDate();
        }

        @Override
        public boolean isValidForDisplay() {
            return mDate != null;
        }

        public Date getDate() {
            return mDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvDate)) return false;
            if (!super.equals(o)) return false;

            GvDate gvDate = (GvDate) o;

            return !(mDate != null ? !mDate.equals(gvDate.mDate) : gvDate.mDate != null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
            return result;
        }
    }
}
