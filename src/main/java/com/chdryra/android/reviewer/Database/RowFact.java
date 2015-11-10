package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFact implements ReviewDataRow, DataFact {
    public static final String COLUMN_FACT_ID = "fact_id";
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_IS_URL = "is_url";

    private static final String SEPARATOR = ":";

    private String mFactId;
    private String mReviewId;
    private String mLabel;
    private String mValue;
    private boolean mIsUrl;

    //Constructors
    public RowFact(DataFact fact, int index) {
        mReviewId = fact.getReviewId();
        mFactId = mReviewId + SEPARATOR + "f" + String.valueOf(index);
        mLabel = fact.getLabel();
        mValue = fact.getValue();
        mIsUrl = fact.isUrl();
    }

    //Via reflection
    public RowFact() {
    }

    public RowFact(Cursor cursor) {
        mFactId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FACT_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mLabel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL));
        mValue = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
        mIsUrl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_URL)) == 1;
    }

    //Overridden

    @Override
    public String getReviewId() {
        return mReviewId;
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
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FACT_ID, mFactId);
        values.put(COLUMN_REVIEW_ID, mReviewId);
        values.put(COLUMN_LABEL, mLabel);
        values.put(COLUMN_VALUE, mValue);
        values.put(COLUMN_IS_URL, mIsUrl);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
