package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 15/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCriterionList extends MdDataList<MdCriterionList.MdCriterion> {
    //Constructors
    public MdCriterionList(ReviewId reviewId) {
        super(reviewId);
    }

    public MdCriterionList(IdableList<Review> criteria, ReviewId parentId) {
        super(parentId);
        for (Review criterion : criteria) {
            add(new MdCriterion(criterion, getReviewId()));
        }
    }

    //Classes
    public static class MdCriterion implements MdData {
        private ReviewId mParentId;
        private Review mCriterion;

        //Constructors
        public MdCriterion(Review criterion, ReviewId parent) {
            mParentId = parent;
            mCriterion = criterion;
        }

        //public methods
        public String getSubject() {
            return mCriterion.getSubject().get();
        }

        public float getRating() {
            return mCriterion.getRating().getValue();
        }

        public Review getReview() {
            return mCriterion;
        }

        //Overridden
        @Override
        public ReviewId getReviewId() {
            return mParentId;
        }

        @Override
        public boolean hasData() {
            return mCriterion != null && mParentId != null;
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
