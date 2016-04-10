/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowValues;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImpl extends RowTableBasic<RowTag> implements RowTag {
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
    protected RowEntry<RowTag, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowTag.class, TAG, mTag);
        } else if(position == 1){
            return new RowEntryImpl<>(RowTag.class, REVIEWS, mReviews);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowTagImpl)) return false;

        RowTagImpl that = (RowTagImpl) o;

        if (mTag != null ? !mTag.equals(that.mTag) : that.mTag != null) return false;
        return !(mReviews != null ? !mReviews.equals(that.mReviews) : that.mReviews != null);

    }

    @Override
    public int hashCode() {
        int result = mTag != null ? mTag.hashCode() : 0;
        result = 31 * result + (mReviews != null ? mReviews.hashCode() : 0);
        return result;
    }
}
