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

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTag implements ReviewerDbRow.TableRow {
    private static final String SEPARATOR = ",";
    public static String TAG     = ReviewerDbContract.TableTags.COLUMN_NAME_TAG;
    public static String REVIEWS = ReviewerDbContract.TableTags.COLUMN_NAME_REVIEWS;

    private String mTag;
    private String mReviews;

    public RowTag() {
    }

    public RowTag(TagsManager.ReviewTag tag) {
        mTag = tag.get();
        mReviews = "";
        for (ReviewId id : tag.getReviews()) {
            mReviews += id.toString() + SEPARATOR;
        }
        mReviews = mReviews.substring(0, mReviews.length() - 1);
    }

    public RowTag(Cursor cursor) {
        mTag = cursor.getString(cursor.getColumnIndexOrThrow(TAG));
        mReviews = cursor.getString(cursor.getColumnIndexOrThrow(REVIEWS));
    }

    @Override
    public String getRowId() {
        return mTag;
    }

    @Override
    public String getRowIdColumnName() {
        return TAG;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TAG, mTag);
        values.put(REVIEWS, mReviews);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }

    public String getTag() {
        return mTag;
    }

    public ArrayList<String> getReviewIds() {
        return new ArrayList<>(Arrays.asList(mReviews.split(SEPARATOR)));
    }
}
