package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;

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
    private ArrayList<T> mData;

    public IdableDataCollection() {
        mData = new ArrayList<>();
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
