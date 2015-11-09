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
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorList extends GvDataList<GvAuthorList.GvAuthor> {
    public static final Parcelable.Creator<GvAuthorList> CREATOR = new Parcelable
            .Creator<GvAuthorList>() {
        //Overridden
        public GvAuthorList createFromParcel(Parcel in) {
            return new GvAuthorList(in);
        }

        public GvAuthorList[] newArray(int size) {
            return new GvAuthorList[size];
        }
    };

    //Constructors
    public GvAuthorList() {
        super(GvAuthor.TYPE, null);
    }

    public GvAuthorList(Parcel in) {
        super(in);
    }

    public GvAuthorList(GvReviewId id) {
        super(GvAuthor.TYPE, id);
    }

    public GvAuthorList(GvAuthorList data) {
        super(data);
    }

//Classes

    /**
     * {@link } version of: {@link com.chdryra.android.reviewer.Model.UserData.Author}
     * {@link ViewHolder}: {@link VhAuthor}
     * <p/>
     * <p>
     * Ignores case when comparing authors.
     * </p>
     */
    public static class GvAuthor extends GvDataBasic<GvAuthor> implements DataAuthor{
        public static final GvDataType<GvAuthor> TYPE =
                new GvDataType<>(GvAuthor.class, "author");
        public static final Parcelable.Creator<GvAuthor> CREATOR = new Parcelable
                .Creator<GvAuthor>() {
            //Overridden
            public GvAuthor createFromParcel(Parcel in) {
                return new GvAuthor(in);
            }

            public GvAuthor[] newArray(int size) {
                return new GvAuthor[size];
            }
        };

        private String mName;
        private String mUserId;

        //Constructors
        public GvAuthor() {
            this(null, null, null);
        }

        public GvAuthor(String name, String userId) {
            this(null, name, userId);
        }

        public GvAuthor(GvReviewId id, String name, String userId) {
            super(GvAuthor.TYPE, id);
            mName = name;
            mUserId = userId;
        }

        public GvAuthor(GvAuthor author) {
            this(author.getReviewIdObject(), author.getName(), author.getUserId());
        }

        GvAuthor(Parcel in) {
            super(in);
            mName = in.readString();
            mUserId = in.readString();
        }

        //Overridden
        @Override
        public String getName() {
            return mName;
        }

        @Override
        public String getUserId() {
            return mUserId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(mName);
            parcel.writeString(mUserId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvAuthor)) return false;
            if (!super.equals(o)) return false;

            GvAuthor gvAuthor = (GvAuthor) o;

            if (mName != null ? !mName.equals(gvAuthor.mName) : gvAuthor.mName != null)
                return false;
            return !(mUserId != null ? !mUserId.equals(gvAuthor.mUserId) : gvAuthor.mUserId !=
                    null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mName != null ? mName.hashCode() : 0);
            result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
            return result;
        }

        @Override
        public String getStringSummary() {
            return mName;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhAuthor();
        }

        @Override
        public boolean isValidForDisplay() {
            return mName != null && mName.length() > 0;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return dataValidator.validate(this);
        }
    }
}

