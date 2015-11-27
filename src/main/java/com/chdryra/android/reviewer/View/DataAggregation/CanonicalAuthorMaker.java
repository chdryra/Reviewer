/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthorMaker implements CanonicalDatumMaker<DataAuthorReview> {
    @Override
    public DataAuthorReview getCanonical(IdableList<DataAuthorReview> data) {
        DatumAuthorReview nullAuthor = new DatumAuthorReview(data.getReviewId(), "", "");
        if (data.size() == 0) return nullAuthor;

        DataAuthorReview reference = data.getItem(0);
        ComparitorAuthor comparitor = new ComparitorAuthor();
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthorReview author : data) {
            if (!comparitor.compare(reference, author).lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthorReview(data.getReviewId(), reference.getName(), reference.getUserId());
    }
}
