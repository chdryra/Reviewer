/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataUrl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

import java.net.URL;

public class GvUrlList extends GvDataList<GvUrlList.GvUrl> {
    public static final Parcelable.Creator<GvUrlList> CREATOR = new Parcelable
            .Creator<GvUrlList>() {
        //Overridden
        public GvUrlList createFromParcel(Parcel in) {
            return new GvUrlList(in);
        }

        public GvUrlList[] newArray(int size) {
            return new GvUrlList[size];
        }
    };

    //Constructors
    public GvUrlList() {
        super(GvUrl.TYPE, null);
    }

    public GvUrlList(Parcel in) {
        super(in);
    }

    public GvUrlList(GvReviewId id) {
        super(GvUrl.TYPE, id);
    }

    public GvUrlList(GvUrlList data) {
        super(data);
    }

//Classes

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdUrlList.MdUrl}
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     */
    public static class GvUrl extends GvFactList.GvFact implements DataUrl {
        public static final GvDataType<GvUrl> TYPE = new GvDataType<>(GvUrl.class, "link");
        public static final Parcelable.Creator<GvUrl> CREATOR = new Parcelable
                .Creator<GvUrl>() {
            //Overridden
            public GvUrl createFromParcel(Parcel in) {
                return new GvUrl(in);
            }

            public GvUrl[] newArray(int size) {
                return new GvUrl[size];
            }
        };

        private URL mUrl;

        //Constructors
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

        public GvUrl(GvUrl url) {
            this(url.getReviewId(), url.getLabel(), url.getUrl());
        }

        private GvUrl(Parcel in) {
            super(in);
            mUrl = (URL) in.readSerializable();
        }

        //Overridden
        @Override
        public GvDataType<GvUrl> getGvDataType() {
            return GvUrl.TYPE;
        }

        @Override
        public boolean isValidForDisplay() {
            return getLabel() != null && getLabel().length() > 0 && mUrl != null;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return dataValidator.validate(this);
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
