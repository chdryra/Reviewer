package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataRow;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableRowList<T extends ReviewDataRow> extends AbstractCollection<T> implements IdableList<T> {
    String mReviewid;
    private ArrayList<T> mData;

    public IdableRowList(String reviewid, ArrayList<T> data) {
        mReviewid = reviewid;
        mData = data;
    }

    @Override
    public String getReviewId() {
        return mReviewid;
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
