/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

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
    private final ReviewId mReviewId;
    private final ArrayList<T> mData;

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
    public DataSize getDataSize() {
        return new DatumSize(getReviewId(), size());
    }

    @Override
    public T get(int position) {
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
