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

        URL get() {
            return mUrl;
        }
    }
}
