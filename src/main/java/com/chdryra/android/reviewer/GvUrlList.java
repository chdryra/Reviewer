/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.net.URL;

public class GvUrlList extends GvDataList<GvUrlList.GvUrl> {
    public static final GvDataType TYPE = new GvDataType("link");

    public GvUrlList() {
        super(TYPE);
    }

    /**
     * {@link GvDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdUrlList.MdUrl}
     * {@link ViewHolder}: {@link VhUrl}
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
            super(label, url.toExternalForm());
            mUrl = url;
        }

        private GvUrl(Parcel in) {
            super(in.readString(), in.readString());
            mUrl = (URL) in.readSerializable();
        }

        @Override
        public boolean isUrl() {
            return true;
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getValue() {
            return toShortenedString();
        }

        @Override
        public URL getUrl() {
            return mUrl;
        }

        public String toShortenedString() {
            String protocol = mUrl.getProtocol();
            String result = toString().replaceFirst(protocol + ":", "");
            if (result.startsWith("//")) {
                result = result.substring(2);
            }

            result = result.trim();
            if (result.endsWith("/")) {
                result = (String) result.subSequence(0, result.length() - 1);
            }

            return result;
        }

        @Override
        public String toString() {
            return mUrl != null ? mUrl.toExternalForm() : null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(getLabel());
            parcel.writeString(super.getValue());
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
