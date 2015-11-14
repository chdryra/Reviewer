package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTag implements DbTableRow {
    public static final String COLUMN_TAG = "tag";
    public static final String COLUMN_REVIEWS = "reviews";

    private static final String SEPARATOR = ",";

    private String mTag;
    private String mReviews;

    //Constructors
    public RowTag(ItemTag tag) {
        mTag = tag.getTag();
        mReviews = "";
        for (String id : tag.getItemIds()) {
            mReviews += id + SEPARATOR;
        }
        mReviews = mReviews.substring(0, mReviews.length() - 1);
    }

    //Via reflection
    public RowTag() {
    }

    public RowTag(Cursor cursor) {
        mTag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAG));
        mReviews = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEWS));
    }

    //public methods
    public String getTag() {
        return mTag;
    }

    public ArrayList<String> getReviewIds() {
        return new ArrayList<>(Arrays.asList(mReviews.split(SEPARATOR)));
    }

    //Overridden
    @Override
    public String getRowId() {
        return mTag;
    }

    @Override
    public String getRowIdColumnName() {
        return COLUMN_TAG;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAG, mTag);
        values.put(COLUMN_REVIEWS, mReviews);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(getRowId());
    }
}
