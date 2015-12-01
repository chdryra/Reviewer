package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataList<T extends DataReviewIdable> extends AbstractCollection<T> implements IdableList<T> {
    private String mReviewId;
    private ArrayList<T> mData;

    public IdableDataList(String reviewId) {
        mReviewId = reviewId;
        mData = new ArrayList<>();
    }

    @Override
    public String getReviewId() {
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
