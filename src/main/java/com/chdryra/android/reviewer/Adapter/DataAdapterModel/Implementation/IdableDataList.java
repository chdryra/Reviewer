package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataList<D extends DataReviewIdable> implements IdableList<D> {
    private String mReviewId;
    private ArrayList<D> mData;

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
    public D getItem(int position) {
        return mData.get(position);
    }

    @Override
    public void add(D datum) {
        mData.add(datum);
    }

    @Override
    public void addCollection(IdableCollection<D> data) {
        for (D datum : data) {
            mData.add(datum);
        }
    }

    @Override
    public Iterator<D> iterator() {
        return mData.iterator();
    }
}
