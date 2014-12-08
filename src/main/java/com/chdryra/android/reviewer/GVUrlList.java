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

public class GVUrlList extends GVReviewDataList<GVUrlList.GVUrl> {

    GVUrlList() {
        super(GVType.URLS);
    }

    void add(String urlString) throws MalformedURLException, URISyntaxException {
        add(new GVUrl(urlString));
    }

    void add(URL url) {
        add(new GVUrl(url));
    }

    /**
     * {@link GVReviewData} version of: {@link RDUrl}
     * {@link ViewHolder}: {@link VHUrl}
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     */
    public static class GVUrl implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVUrl> CREATOR = new Parcelable
                .Creator<GVUrl>() {
            public GVUrl createFromParcel(Parcel in) {
                return new GVUrl(in);
            }

            public GVUrl[] newArray(int size) {
                return new GVUrl[size];
            }
        };
        private URL mUrl;

        public GVUrl(URL url) {
            mUrl = url;
        }

        private GVUrl(Parcel in) {
            mUrl = (URL) in.readSerializable();
        }

        public GVUrl(String stringUrl) throws MalformedURLException, URISyntaxException {
            URL url = new URL(URLUtil.guessUrl(stringUrl));
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            mUrl = uri.toURL();
        }

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
            return new VHUrl();
        }

        @Override
        public boolean isValidForDisplay() {
            return toShortenedString() != null && toShortenedString().length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVUrl)) return false;

            GVUrl gvUrl = (GVUrl) o;

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
