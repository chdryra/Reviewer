package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataList<T extends DataReviewIdable> extends SortableListImpl<T> implements IdableList<T>{
    private MdReviewId mReviewId;

    public MdDataList(MdReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    public boolean containsId(String id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return true;
        }

        return false;
    }

    public T getItem(String id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return datum;
        }

        return null;
    }

    public void remove(String reviewId) {
        mData.remove(getItem(reviewId));
    }

    protected MdReviewId getMdReviewId() {
        return mReviewId;
    }
}
