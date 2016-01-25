/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowFact;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactImpl extends RowTableBasic implements RowFact {
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
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(FACT_ID, mFactId);
        } else if(position == 1) {
            return new RowEntryImpl<>(REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(LABEL, mLabel);
        } else if(position == 3) {
            return new RowEntryImpl<>(VALUE, mValue);
        } else if(position == 4){
            return new RowEntryImpl<>(IS_URL, mIsUrl);
        } else {
            throw noElement();
        }
    }
}
