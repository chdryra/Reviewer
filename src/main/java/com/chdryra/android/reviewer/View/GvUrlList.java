/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Controller.DataUrl;
import com.chdryra.android.reviewer.Controller.DataValidator;

import java.net.URL;

public class GvUrlList extends GvDataList<GvUrlList.GvUrl> {
    public static final GvDataType TYPE = new GvDataType("link");

    public GvUrlList() {
        super(GvUrl.class, TYPE);
    }

    public GvUrlList(GvReviewId id, GvUrlList data) {
        super(id, data);
    }

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdUrlList.MdUrl}
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     */
    public static class GvUrl extends GvFactList.GvFact implements DataUrl {
        public static final Parcelable.Creator<GvUrl> CREATOR = new Parcelable
                .Creator<GvUrl>() {
            public GvUrl createFromParcel(Parcel in) {
                return new GvUrl(in);
            }

            public GvUrl[] newArray(int size) {
                return new GvUrl[size];
            }
        };

        private URL mUrl;

        public GvUrl() {
            super();
        }

        public GvUrl(String label, URL url) {
            super(label, TextUtils.toShortenedString(url));
            mUrl = url;
        }

        public GvUrl(GvReviewId id, String label, URL url) {
            super(id, label, TextUtils.toShortenedString(url));
            mUrl = url;
        }

        private GvUrl(Parcel in) {
            super(in);
            mUrl = (URL) in.readSerializable();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public boolean isUrl() {
            return true;
        }

        @Override
        public URL getUrl() {
            return mUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeSerializable(mUrl);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvUrl)) return false;
            if (!super.equals(o)) return false;

            GvUrl gvUrl = (GvUrl) o;

            if (mUrl != null ? !mUrl.equals(gvUrl.mUrl) : gvUrl.mUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
            return result;
        }
    }
}
