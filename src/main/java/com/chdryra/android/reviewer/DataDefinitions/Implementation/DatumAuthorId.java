/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorId implements AuthorId, Parcelable{
    public static final Creator<DatumAuthorId> CREATOR = new Creator<DatumAuthorId>() {
        @Override
        public DatumAuthorId createFromParcel(Parcel in) {
            return new DatumAuthorId(in);
        }

        @Override
        public DatumAuthorId[] newArray(int size) {
            return new DatumAuthorId[size];
        }
    };

    private String mId;

    public DatumAuthorId(String id) {
        mId = id;
    }

    public DatumAuthorId(Parcel in) {
        mId = in.readString();
    }

    @Override
    public String toString() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorId)) return false;

        AuthorId that = (AuthorId) o;

        return !(mId != null ? !mId.equals(that.toString()) : that.toString() != null);

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
