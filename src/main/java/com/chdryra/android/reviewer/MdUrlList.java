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
public class MdUrlList extends MdDataList<MdUrlList.MdUrl> {

    public MdUrlList(Review holdingReview) {
        super(holdingReview);
    }

    /**
     * Review Data: URL
     * <p>
     * {@link #hasData()}: non-null URL.
     * </p>
     */
    public static class MdUrl implements MdData, DataUrl {
        private final URL    mUrl;
        private       Review mHoldingReview;

        public MdUrl(URL url, Review holdingReview) {
            mUrl = url;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public boolean hasData() {
            return mUrl != null;
        }

        @Override
        public URL getUrl() {
            return mUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MdUrl)) return false;

            MdUrl mdUrl = (MdUrl) o;

            if (mHoldingReview != null ? !mHoldingReview.equals(mdUrl.mHoldingReview) : mdUrl
                    .mHoldingReview != null) {
                return false;
            }
            if (mUrl != null ? !mUrl.equals(mdUrl.mUrl) : mdUrl.mUrl != null) return false;

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
