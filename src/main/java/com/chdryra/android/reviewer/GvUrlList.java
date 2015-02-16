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
import android.webkit.URLUtil;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GvUrlList extends GvDataList<GvUrlList.GvUrl> {
    public static final GvType TYPE = GvType.URLS;

    public GvUrlList() {
        super(TYPE);
    }

    /**
     * {@link GvDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdUrlList.MdUrl}
     * {@link ViewHolder}: {@link VholderUrl}
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     */
    public static class GvUrl implements GvDataList.GvData, DataUrl {
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
        }

        public GvUrl(URL url) {
            mUrl = url;
        }

        private GvUrl(Parcel in) {
            mUrl = (URL) in.readSerializable();
        }

        public GvUrl(String stringUrl) throws MalformedURLException, URISyntaxException {
            URL url = new URL(URLUtil.guessUrl(stringUrl));
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            mUrl = uri.toURL();
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
        public ViewHolder newViewHolder() {
            return new VholderUrl();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this) && DataValidator.validateString(toShortenedString
                    ());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvUrl)) return false;

            GvUrl gvUrl = (GvUrl) o;

            return !(mUrl != null ? !mUrl.equals(gvUrl.mUrl) : gvUrl.mUrl != null);

        }

        @Override
        public int hashCode() {
            return mUrl != null ? mUrl.hashCode() : 0;
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
            parcel.writeSerializable(mUrl);
        }
    }
}
