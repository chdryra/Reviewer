package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewDataRow;

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
    public Iterator<T> iterator() {
        return mData.iterator();
    }
}
