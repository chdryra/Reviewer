/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDUrlList extends RDList<RDUrlList.RDUrl> {
    /**
     * Review Data: URL
     * <p>
     * {@link #hasData()}: non-null URL.
     * </p>
     */
    public static class RDUrl implements RData {
        private final URL    mUrl;
        private       Review mHoldingReview;

        public RDUrl(URL url, Review holdingReview) {
            mUrl = url;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public void setHoldingReview(Review review) {
            mHoldingReview = review;
        }

        @Override
        public boolean hasData() {
            return mUrl != null;
        }

        public URL getUrl() {
            return mUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RDUrl)) return false;

            RDUrl rdUrl = (RDUrl) o;

            if (mHoldingReview != null ? !mHoldingReview.equals(rdUrl.mHoldingReview) : rdUrl
                    .mHoldingReview != null) {
                return false;
            }
            if (mUrl != null ? !mUrl.equals(rdUrl.mUrl) : rdUrl.mUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = mUrl != null ? mUrl.hashCode() : 0;
            result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
            return result;
        }
    }
}
