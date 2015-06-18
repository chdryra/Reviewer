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
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorList extends GvDataList<GvAuthorList.GvAuthor> {
    public static final GvDataType      TYPE       = new GvDataType("author");
    public static final Class<GvAuthor> DATA_CLASS = GvAuthor.class;

    public GvAuthorList() {
        super(null, DATA_CLASS, TYPE);
    }

    public GvAuthorList(GvReviewId id) {
        super(id, DATA_CLASS, TYPE);
    }

    public GvAuthorList(GvAuthorList data) {
        super(data);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> authors = new ArrayList<>();
        for (GvAuthor author : this) {
            authors.add(author.getName());
        }

        return authors;
    }

    @Override
    protected Comparator<GvAuthor> getDefaultComparator() {
        return new Comparator<GvAuthor>() {

            @Override
            public int compare(GvAuthor lhs, GvAuthor rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        };
    }

    /**
     * {@link } version of: {@link com.chdryra.android.reviewer.Model.UserData.Author}
     * {@link ViewHolder}: {@link VhAuthor}
     * <p/>
     * <p>
     * Ignores case when comparing authors.
     * </p>
     */
    public static class GvAuthor extends GvDataBasic {
        public static final Parcelable.Creator<GvAuthor> CREATOR = new Parcelable
                .Creator<GvAuthor>() {
            public GvAuthor createFromParcel(Parcel in) {
                return new GvAuthor(in);
            }

            public GvAuthor[] newArray(int size) {
                return new GvAuthor[size];
            }
        };
        private String mName;
        private String mUserId;

        public GvAuthor() {
            this(null, null, null);
        }

        public GvAuthor(String name, String userId) {
            this(null, name, userId);
        }

        public GvAuthor(GvReviewId id, String name, String userId) {
            super(TYPE, id);
            mName = name;
            mUserId = userId;
        }

        public GvAuthor(GvAuthor author) {
            this(author.getReviewId(), author.getName(), author.getUserId());
        }

        GvAuthor(Parcel in) {
            super(in);
            mName = in.readString();
            mUserId = in.readString();
        }

        @Override
        public GvDataType getGvDataType() {
            return GvAuthorList.TYPE;
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
        public String getStringSummary() {
            return mName;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhAuthor();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validateString(mName) && DataValidator.validateString(mUserId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvAuthor)) return false;

            GvAuthor gvAuthor = (GvAuthor) o;

            if (mName != null ? !mName.equals(gvAuthor.mName) : gvAuthor.mName != null) {
                return false;
            }
            return !(mUserId != null ? !mUserId.equals(gvAuthor.mUserId) : gvAuthor.mUserId !=
                    null);

        }

        @Override
        public int hashCode() {
            int result = mName != null ? mName.hashCode() : 0;
            result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
            return result;
        }

        public String getName() {
            return mName;
        }

        public String getUserId() {
            return mUserId;
        }
    }
}

