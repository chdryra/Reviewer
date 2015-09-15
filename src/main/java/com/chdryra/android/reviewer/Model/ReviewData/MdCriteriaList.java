package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 15/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCriteriaList extends MdDataList<MdCriteriaList.MdCriterion> {
    public MdCriteriaList(ReviewId reviewId) {
        super(reviewId);
    }

    public MdCriteriaList(ReviewNode parent) {
        super(parent.getId());
        for (ReviewNode criterion : parent.getChildren()) {
            add(new MdCriterion(criterion.getReview(), getReviewId()));
        }
    }

    public static class MdCriterion implements MdData {
        private ReviewId mParentId;
        private Review mCriterion;

        public MdCriterion(ReviewId parent) {
            mParentId = parent;
        }

        public MdCriterion(Review criterion, ReviewId parent) {
            mParentId = parent;
            mCriterion = criterion;
        }

        public String getSubject() {
            return mCriterion.getSubject().get();
        }

        public float getRating() {
            return mCriterion.getRating().get();
        }

        public Review getReview() {
            return mCriterion;
        }

        @Override
        public ReviewId getReviewId() {
            return mParentId;
        }

        @Override
        public boolean hasData() {
            return mCriterion != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MdCriterion)) return false;

            MdCriterion that = (MdCriterion) o;

            if (!mParentId.equals(that.mParentId)) return false;
            return !(mCriterion != null ? !mCriterion.equals(that.mCriterion) : that.mCriterion
                    != null);

        }

        @Override
        public int hashCode() {
            int result = mParentId.hashCode();
            result = 31 * result + (mCriterion != null ? mCriterion.hashCode() : 0);
            return result;
        }
    }
}
