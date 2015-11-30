package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdIdableList<T extends DataReviewIdable> extends SortableListImpl<T> implements IdableList<T>{
    private MdReviewId mReviewId;

    public MdIdableList(MdReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public void addCollection(IdableCollection<? extends T> items) {
        super.addList(items);
    }

    public boolean containsId(String id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return true;
        }

        return false;
    }

    public T get(String id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return datum;
        }

        return null;
    }

    public void remove(String reviewId) {
        mData.remove(get(reviewId));
    }

    protected MdReviewId getMdReviewId() {
        return mReviewId;
    }
}
