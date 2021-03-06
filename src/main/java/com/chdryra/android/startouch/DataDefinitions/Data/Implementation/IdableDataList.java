/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataList<T extends HasReviewId> extends IdableDataCollection<T>
        implements IdableList<T> {
    private final ReviewId mReviewId;

    public IdableDataList() {
        mReviewId = new DatumReviewId();
    }

    public IdableDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public DataSize getDataSize() {
        return new DatumSize(getReviewId(), size());
    }
}
