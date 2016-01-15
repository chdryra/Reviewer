package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImpl extends RowTableBasic implements RowTag {
    private static final String SEPARATOR = ",";

    private String mTag;
    private String mReviews;

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

    public RowTagImpl(RowValues values) {
        mTag = values.getValue(TAG.getName(), TAG.getType());
        mReviews = values.getValue(REVIEWS.getName(), REVIEWS.getType());
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public String getReviewIdsString() {
        return mReviews;
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
        return TAG.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(getRowId());
    }

    @Override
    protected int size() {
        return 2;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(TAG, mTag);
        } else {
            return new RowEntryImpl<>(REVIEWS, mReviews);
        }
    }
}
