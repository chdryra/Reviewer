/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowValues;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactImpl extends RowTableBasic<RowFact> implements RowFact {
    private static final String SEPARATOR = ":";

    private String mFactId;
    private String mReviewId;
    private String mLabel;
    private String mValue;
    private boolean mIsUrl;

    private boolean mValidIsUrl = true;

    //Constructors
    public RowFactImpl(DataFact fact, int index) {
        mReviewId = fact.getReviewId().toString();
        mFactId = mReviewId + SEPARATOR + "f" + String.valueOf(index);
        mLabel = fact.getLabel();
        mValue = fact.getValue();
        mIsUrl = fact.isUrl();
    }

    //Via reflection
    public RowFactImpl() {
    }

    public RowFactImpl(RowValues values) {
        mFactId = values.getValue(FACT_ID.getName(), FACT_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mLabel = values.getValue(LABEL.getName(), LABEL.getType());
        mValue = values.getValue(VALUE.getName(), VALUE.getType());
        Boolean isUrl = values.getValue(IS_URL.getName(), IS_URL.getType());
        if(isUrl == null) mValidIsUrl = false;
        mIsUrl = mValidIsUrl && isUrl;
    }

    //Overridden

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
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
        return mIsUrl;
    }

    @Override
    public String getRowId() {
        return mFactId;
    }

    @Override
    public String getRowIdColumnName() {
        return FACT_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return mValidIsUrl && validator.validateString(getLabel()) &&
                validator.validateString(getValue()) && validator.validateString(mFactId)
                && validator.validateString(mReviewId);
    }

    @Override
    protected int size() {
        return 5;
    }

    @Override
    protected RowEntry<RowFact, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowFact.class, FACT_ID, mFactId);
        } else if(position == 1) {
            return new RowEntryImpl<>(RowFact.class, REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(RowFact.class, LABEL, mLabel);
        } else if(position == 3) {
            return new RowEntryImpl<>(RowFact.class, VALUE, mValue);
        } else if(position == 4){
            return new RowEntryImpl<>(RowFact.class, IS_URL, mIsUrl);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowFactImpl)) return false;

        RowFactImpl that = (RowFactImpl) o;

        if (mIsUrl != that.mIsUrl) return false;
        if (mValidIsUrl != that.mValidIsUrl) return false;
        if (mFactId != null ? !mFactId.equals(that.mFactId) : that.mFactId != null) return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        if (mLabel != null ? !mLabel.equals(that.mLabel) : that.mLabel != null) return false;
        return !(mValue != null ? !mValue.equals(that.mValue) : that.mValue != null);

    }

    @Override
    public int hashCode() {
        int result = mFactId != null ? mFactId.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mLabel != null ? mLabel.hashCode() : 0);
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        result = 31 * result + (mIsUrl ? 1 : 0);
        result = 31 * result + (mValidIsUrl ? 1 : 0);
        return result;
    }
}
