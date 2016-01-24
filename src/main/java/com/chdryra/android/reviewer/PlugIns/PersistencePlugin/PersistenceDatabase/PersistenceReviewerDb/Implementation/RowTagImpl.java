package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;

import org.apache.commons.lang3.StringUtils;

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
        mReviews = StringUtils.join(tag.getItemIds().toArray(), SEPARATOR);
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
        return validator.validateString(getRowId()) && validator.validateString(mReviews);
    }

    @Override
    protected int size() {
        return 2;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(TAG, mTag);
        } else if(position == 1){
            return new RowEntryImpl<>(REVIEWS, mReviews);
        } else {
            throw noElement();
        }
    }
}
