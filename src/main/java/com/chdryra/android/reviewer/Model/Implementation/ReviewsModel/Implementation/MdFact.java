package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;

/**
 * Review Data: fact
 */
public class MdFact implements DataFact {

    private final String mLabel;
    private final String mValue;
    private final MdReviewId mReviewId;

    //Constructors
    public MdFact(MdReviewId reviewId, String label, String value) {
        mLabel = label;
        mValue = value;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
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

        if (mReviewId != null ? !mReviewId.equals(mdFact.mReviewId) : mdFact
                .mReviewId != null) {
            return false;
        }
        if (mLabel != null ? !mLabel.equals(mdFact.mLabel) : mdFact.mLabel != null) {
            return false;
        }
        if (mValue != null ? !mValue.equals(mdFact.mValue) : mdFact.mValue != null) {
            return false;
        }
        if (isUrl() != mdFact.isUrl()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mLabel != null ? mLabel.hashCode() : 0;
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
