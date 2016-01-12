package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactImpl implements RowFact {
    private static final String SEPARATOR = ":";

    private String mFactId;
    private String mReviewId;
    private String mLabel;
    private String mValue;
    private boolean mIsUrl;

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
        mFactId = values.getString(COLUMN_FACT_ID);
        mReviewId = values.getString(COLUMN_REVIEW_ID);
        mLabel = values.getString(COLUMN_LABEL);
        mValue = values.getString(COLUMN_VALUE);
        mIsUrl = values.getBoolean(COLUMN_IS_URL);
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
        return COLUMN_FACT_ID;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
