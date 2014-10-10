/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * GVReviewDataList: GVUrl
 * ViewHolder: VHUrlView
 *
 * @see com.chdryra.android.reviewer.FragmentReviewURLs
 * @see com.chdryra.android.reviewer.VHUrlView
 */
class GVUrlList extends GVReviewDataList<GVUrlList.GVUrl> {

    GVUrlList() {
        super(GVType.URLS);
    }

    void add(String urlString) throws MalformedURLException, URISyntaxException {
        add(new GVUrl(urlString));
    }

    void add(URL url) {
        add(new GVUrl(url));
    }

    boolean contains(URL url) {
        return contains(new GVUrl(url));
    }

    void remove(URL url) {
        remove(new GVUrl(url));
    }

    /**
     * GVData version of: RDUrl
     * ViewHolder: VHUrlView
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.RDUrl
     * @see com.chdryra.android.reviewer.VHUrlView
     */
    class GVUrl implements GVData {
        URL mUrl;

        private GVUrl(URL url) {
            mUrl = url;
        }

        private GVUrl(String stringUrl) throws MalformedURLException, URISyntaxException {
            URL url = new URL(stringUrl);
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
        public ViewHolder getViewHolder() {
            return new VHUrlView();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            GVUrl other = (GVUrl) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (mUrl == null) {
                if (other.mUrl != null) {
                    return false;
                }
            } else if (!mUrl.equals(other.mUrl)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((mUrl == null) ? 0 : mUrl.hashCode());
            return result;
        }

        public String toString() {
            return mUrl != null ? mUrl.toExternalForm() : null;
        }

        private GVUrlList getOuterType() {
            return GVUrlList.this;
        }
    }
}
