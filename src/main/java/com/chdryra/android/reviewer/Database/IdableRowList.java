package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableRowList<T extends ReviewDataRow> implements IdableList<T> {
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
    public void add(T datum) {
        mData.add(datum);
    }

    @Override
    public void addCollection(IdableCollection<T> data) {
        for(T datum : data) {
            add(datum);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return mData.iterator();
    }
}
