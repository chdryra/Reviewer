/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<DataAuthorReview> {
    private DifferenceComparitor<? super DataAuthorReview, DifferenceBoolean> mComparitor;

    public CanonicalAuthor(DifferenceComparitor<? super DataAuthorReview, DifferenceBoolean> comparitor) {
        mComparitor = comparitor;
    }

    @Override
    public DataAuthorReview getCanonical(IdableList<? extends DataAuthorReview> data) {
        String id = data.getReviewId();
        DatumAuthorReview nullAuthor = new DatumAuthorReview(id, "", "");
        if (data.size() == 0) return nullAuthor;

        DataAuthorReview reference = data.getItem(0);
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthorReview author : data) {
            if (!mComparitor.compare(reference, author).lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthorReview(id, reference.getName(), reference.getUserId());
    }
}
