/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.SortableListImpl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.MdData;

/**
 * Review Data: Sortable collection of {@link MdData} objects that itself is considered Review Data
 *
 * @param <T>: {@link MdData} type in collection.
 */
public class MdDataList<T extends MdData> extends SortableListImpl<T> implements MdData, IdableList<T> {
    private final MdReviewId mReviewId;

    //Constructors
    public MdDataList(MdReviewId reviewId) {
        mReviewId = reviewId;
    }

    protected MdReviewId getMdReviewId() {
        return mReviewId;
    }

    //Overridden

    @Override
    public void addCollection(IdableCollection<T> data) {
        super.addList(data);
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mData.size() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdDataList)) return false;

        MdDataList that = (MdDataList) o;

        if (!mReviewId.equals(that.mReviewId)) {
            return false;
        }
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            if (!getItem(i).equals(that.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        for (T datum : this) {
            result = 31 * result + datum.hashCode();
        }

        return result;
    }
}
