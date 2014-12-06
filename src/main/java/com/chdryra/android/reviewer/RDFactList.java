/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDFactList extends RDList<RDFactList.RDFact> {
    /**
     * Review Data: fact
     * <p>
     * {@link #hasData()}: Label and value strings at least 1 character in length
     * </p>
     */
    public static class RDFact implements RData {

        private final String mLabel;
        private final String mValue;
        private       Review mHoldingReview;

        public RDFact(String label, String value, Review holdingReview) {
            mLabel = label;
            mValue = value;
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
            return mLabel != null && mValue != null && mLabel.length() > 0 && mValue.length() > 0;
        }

        public String getLabel() {
            return mLabel;
        }

        public String getValue() {
            return mValue;
        }
    }
}
