package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataList<T extends HasReviewId> extends SortableListImpl<T> implements IdableList<T>{
    private ReviewId mReviewId;

    public MdDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    public boolean containsId(ReviewId id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Nullable
    public T getItem(ReviewId id) {
        for (T datum : this) {
            if(datum.getReviewId().equals(id)) return datum;
        }

        return null;
    }

    public void remove(ReviewId reviewId) {
        mData.remove(getItem(reviewId));
    }
}
