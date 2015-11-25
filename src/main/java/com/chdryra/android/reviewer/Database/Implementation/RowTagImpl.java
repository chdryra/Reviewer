package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImpl implements RowTag {
    private static final String SEPARATOR = ",";

    private String mTag;
    private String mReviews;

    //Constructors
    public RowTagImpl(ItemTag tag) {
        mTag = tag.getTag();
        mReviews = "";
        for (String id : tag.getItemIds()) {
            mReviews += id + SEPARATOR;
        }
        mReviews = mReviews.substring(0, mReviews.length() - 1);
    }

    //Via reflection
    public RowTagImpl() {
    }

    public RowTagImpl(Cursor cursor) {
        mTag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAG));
        mReviews = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEWS));
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public ArrayList<String> getReviewIds() {
        return new ArrayList<>(Arrays.asList(mReviews.split(SEPARATOR)));
    }

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
