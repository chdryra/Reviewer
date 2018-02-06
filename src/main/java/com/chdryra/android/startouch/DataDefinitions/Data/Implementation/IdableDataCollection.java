/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataCollection<T extends HasReviewId> extends AbstractCollection<T>
        implements IdableCollection<T> {
    private final ArrayList<T> mData;

    public IdableDataCollection() {
        mData = new ArrayList<>();
    }

    @Override
    public int size() {
        return mData.size();
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
    public Iterator<T> iterator() {
        return mData.iterator();
    }
}
