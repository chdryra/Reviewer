package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 15/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCriterionList extends MdDataList<MdCriterionList.MdCriterion> {
    //Constructors
    public MdCriterionList(MdReviewId parentId) {
        super(parentId);
    }

    public MdCriterionList(MdReviewId parentId, MdIdableList<Review> criteria) {
        super(parentId);
        for (Review criterion : criteria) {
            add(new MdCriterion(getMdReviewId(), criterion));
        }
    }

    //Classes
    public static class MdCriterion implements MdData, DataCriterion {
        private MdReviewId mParentId;
        private Review mCriterion;

        //Constructors
        public MdCriterion(MdReviewId parent, Review criterion) {
            mParentId = parent;
            mCriterion = criterion;
        }

        //Overridden
        @Override
        public String getSubject() {
            return mCriterion.getSubject().getSubject();
        }

        @Override
        public float getRating() {
            return mCriterion.getRating().getRating();
        }

        @Override
        public Review getReview() {
            return mCriterion;
        }

        @Override
        public String getReviewId() {
            return mParentId.toString();
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
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
