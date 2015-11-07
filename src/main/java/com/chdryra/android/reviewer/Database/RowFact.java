package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFact implements MdDataRow<MdFactList.MdFact> {
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
    private DataValidator mValidator;

    //Constructors
    public RowFact(MdFactList.MdFact fact, int index, DataValidator validator) {
        mReviewId = fact.getReviewId().toString();
        mFactId = mReviewId + SEPARATOR + "f" + String.valueOf(index);
        mLabel = fact.getLabel();
        mValue = fact.getValue();
        mIsUrl = fact.isUrl();
        mValidator = validator;
    }

    //Via reflection
    public RowFact() {
    }

    public RowFact(Cursor cursor, DataValidator validator) {
        mFactId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FACT_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mLabel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL));
        mValue = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
        mIsUrl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_URL)) == 1;
        mValidator = validator;
    }

    //Overridden
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
    public boolean hasData() {
        return mValidator != null && mValidator.validateString(getRowId());
    }

    public MdFactList.MdFact toMdData() {
        ReviewId id = ReviewId.fromString(mReviewId);
        if (mIsUrl) {
            String urlGuess = URLUtil.guessUrl(mValue.toLowerCase());
            try {
                return new MdUrlList.MdUrl(mLabel, new URL(urlGuess), id);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new MdFactList.MdFact(mLabel, mValue, id);
    }
}
