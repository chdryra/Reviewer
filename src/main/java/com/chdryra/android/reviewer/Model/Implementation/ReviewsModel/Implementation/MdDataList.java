/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.MdData;

/**
 * Review Data: Sortable collection of {@link MdData} objects that itself is considered Review Data
 *
 * @param <T>: {@link MdData} type in collection.
 */
public class MdDataList<T extends MdData> extends MdIdableList<T> implements MdData, IdableList<T> {
    public MdDataList(MdReviewId reviewId) {
        super(reviewId);
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mData.size() > 0;
    }
}
