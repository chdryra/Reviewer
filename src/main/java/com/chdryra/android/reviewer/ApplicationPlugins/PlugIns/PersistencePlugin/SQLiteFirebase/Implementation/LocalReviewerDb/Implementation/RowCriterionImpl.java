/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;


/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCriterionImpl extends RowTableBasic<RowCriterion> implements RowCriterion {
    private static final String SEPARATOR = ":";
    
    private String mCriterionId;
    private String mReviewId;
    private String mSubject;
    private float mRating;

    public RowCriterionImpl(DataCriterion criterion, int index) {
        mReviewId = criterion.getReviewId().toString();
        mCriterionId = mReviewId + SEPARATOR + "cr" + String.valueOf(index);
        mSubject = criterion.getSubject();
        mRating = criterion.getRating();
    }

    //Via reflection
    public RowCriterionImpl() {
    }

    public RowCriterionImpl(RowValues values) {
        mCriterionId = values.getValue(CRITERION_ID.getName(), CRITERION_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mSubject = values.getValue(SUBJECT.getName(), SUBJECT.getType());
        Float rating = values.getValue(RATING.getName(), RATING.getType());
        mRating = rating != null ? rating : -1f;
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public String getRowId() {
        return mCriterionId;
    }

    @Override
    public String getRowIdColumnName() {
        return CRITERION_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(mCriterionId) && validator.validate(getReviewId()) && validator.validate(this);
    }

    @Override
    protected int size() {
        return 4;
    }

    @Override
    protected RowEntry<RowCriterion, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowCriterion.class, CRITERION_ID, mCriterionId);
        } else if(position == 1) {
            return new RowEntryImpl<>(RowCriterion.class, REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(RowCriterion.class, SUBJECT, mSubject);
        } else if(position == 3){
            return new RowEntryImpl<>(RowCriterion.class, RATING, mRating);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowCriterionImpl)) return false;

        RowCriterionImpl that = (RowCriterionImpl) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (mCriterionId != null ? !mCriterionId.equals(that.mCriterionId) : that.mCriterionId !=
                null)
            return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return mSubject != null ? mSubject.equals(that.mSubject) : that.mSubject == null;

    }

    @Override
    public int hashCode() {
        int result = mCriterionId != null ? mCriterionId.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        return result;
    }
}
