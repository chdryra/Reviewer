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
public class MdFactList extends MdDataList<MdFactList.MdFact> {

    public MdFactList(Review holdingReview) {
        super(holdingReview);
    }

    /**
     * Review Data: fact
     * <p>
     * {@link #hasData()}: Label and value strings at least 1 character in length
     * </p>
     */
    public static class MdFact implements MdData, DataFact {

        private final String mLabel;
        private final String mValue;
        private final Review mHoldingReview;

        public MdFact(String label, String value, Review holdingReview) {
            mLabel = label;
            mValue = value;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validate(this);
        }

        @Override
        public String getLabel() {
            return mLabel;
        }

        @Override
        public String getValue() {
            return mValue;
        }

        @Override
        public boolean isUrl() {
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MdFact)) return false;

            MdFact mdFact = (MdFact) o;

            if (mHoldingReview != null ? !mHoldingReview.equals(mdFact.mHoldingReview) : mdFact
                    .mHoldingReview != null) {
                return false;
            }
            if (mLabel != null ? !mLabel.equals(mdFact.mLabel) : mdFact.mLabel != null) {
                return false;
            }
            if (mValue != null ? !mValue.equals(mdFact.mValue) : mdFact.mValue != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mLabel != null ? mLabel.hashCode() : 0;
            result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
            result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
            return result;
        }
    }
}
