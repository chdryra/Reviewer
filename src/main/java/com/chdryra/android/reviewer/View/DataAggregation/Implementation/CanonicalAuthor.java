/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<DataAuthorReview> {
    @Override
    public DataAuthorReview getCanonical(IdableList<? extends DataAuthorReview> data) {
        String id = data.getReviewId();
        DatumAuthorReview nullAuthor = new DatumAuthorReview(id, "", "");
        if (data.size() == 0) return nullAuthor;

        DataAuthorReview reference = data.getItem(0);
        ComparitorAuthor comparitor = new ComparitorAuthor();
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthorReview author : data) {
            if (!comparitor.compare(reference, author).lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthorReview(id, reference.getName(), reference.getUserId());
    }
}
