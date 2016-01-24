/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewDataRow;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableRowList<T extends ReviewDataRow> extends AbstractCollection<T>
        implements IdableList<T> {
    ReviewId mReviewId;
    private ArrayList<T> mData;

    public IdableRowList(ReviewId reviewId, ArrayList<T> data) {
        mReviewId = reviewId;
        mData = data;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public boolean add(T datum) {
        return mData.add(datum);
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return mData.iterator();
    }
}
