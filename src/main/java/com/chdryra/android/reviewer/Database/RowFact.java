/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewId;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFact implements ReviewerDbRow.TableRow {
    public static String FACT_ID   = ReviewerDbContract.TableFacts.COLUMN_NAME_FACT_ID;
    public static String REVIEW_ID = ReviewerDbContract.TableFacts.COLUMN_NAME_REVIEW_ID;
    public static String LABEL     = ReviewerDbContract.TableFacts.COLUMN_NAME_LABEL;
    public static String VALUE     = ReviewerDbContract.TableFacts.COLUMN_NAME_VALUE;
    public static String IS_URL    = ReviewerDbContract.TableFacts.COLUMN_NAME_IS_URL;

    private String  mFactId;
    private String  mReviewId;
    private String  mLabel;
    private String  mValue;
    private boolean mIsUrl;

    public RowFact() {
    }

    public RowFact(MdFactList.MdFact fact, int index) {
        mReviewId = fact.getReviewId().toString();
        mFactId = mReviewId + ReviewerDbRow.SEPARATOR + "f" + String.valueOf(index);
        mLabel = fact.getLabel();
        mValue = fact.getValue();
        mIsUrl = fact.isUrl();
    }

    public RowFact(Cursor cursor) {
        mFactId = cursor.getString(cursor.getColumnIndexOrThrow(FACT_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
        mLabel = cursor.getString(cursor.getColumnIndexOrThrow(LABEL));
        mValue = cursor.getString(cursor.getColumnIndexOrThrow(VALUE));
        mIsUrl = cursor.getInt(cursor.getColumnIndexOrThrow(IS_URL)) == 1;
    }

    @Override
    public String getRowId() {
        return mFactId;
    }

    @Override
    public String getRowIdColumnName() {
        return FACT_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FACT_ID, mFactId);
        values.put(REVIEW_ID, mReviewId);
        values.put(LABEL, mLabel);
        values.put(VALUE, mValue);
        values.put(IS_URL, mIsUrl);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
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
