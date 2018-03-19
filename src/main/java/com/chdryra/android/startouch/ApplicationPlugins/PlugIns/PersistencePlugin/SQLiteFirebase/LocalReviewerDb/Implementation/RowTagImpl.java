/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowValues;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImpl extends RowTableBasic<RowTag> implements RowTag {
    private static final String SEPARATOR = ":";

    private String mTagId;
    private String mReviewId;
    private String mTag;

    //Via reflection
    public RowTagImpl(DataTag tag, int index) {
        mReviewId = tag.getReviewId().toString();
        mTagId = mReviewId + SEPARATOR + "t" + String.valueOf(index);
        mTag = tag.getTag();
    }

    public RowTagImpl() {
    }

    public RowTagImpl(RowValues values) {
        mTagId = values.getValue(TAG_ID.getName(), TAG_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mTag = values.getValue(TAG.getName(), TAG.getType());
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public String getRowId() {
        return mTagId;
    }

    @Override
    public String getRowIdColumnName() {
        return TAG_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(getRowId()) && validator.validateString(mTag);
    }

    @Override
    protected int size() {
        return 3;
    }

    @Override
    protected RowEntry<RowTag, ?> getEntry(int position) {
        if (position == 0) {
            return new RowEntryImpl<>(RowTag.class, TAG_ID, mTagId);
        } else if (position == 1) {
            return new RowEntryImpl<>(RowTag.class, REVIEW_ID, mReviewId);
        } else if (position == 2) {
            return new RowEntryImpl<>(RowTag.class, TAG, mTag);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowTagImpl)) return false;

        RowTagImpl that = (RowTagImpl) o;

        if (!mTagId.equals(that.mTagId)) return false;
        if (!mReviewId.equals(that.mReviewId)) return false;
        return mTag.equals(that.mTag);

    }

    @Override
    public int hashCode() {
        int result = mTagId.hashCode();
        result = 31 * result + mReviewId.hashCode();
        result = 31 * result + mTag.hashCode();
        return result;
    }
}
