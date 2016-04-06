/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Collections.SortableListImpl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataCollection<T extends HasReviewId> extends SortableListImpl<T> implements IdableCollection<T> {

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
