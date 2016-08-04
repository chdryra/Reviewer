/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 02/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReferenceWrapper {
    public <T extends HasReviewId> ReviewItemReference<T> newDataReference(ReviewId id, T datum) {
        return new StaticItemReference<>(id, datum);
    }

    public <T extends HasReviewId> ReviewListReference<T> newListReference(ReviewId id, IdableList<T> data) {
        return new StaticListReference<>(id, data);
    }
}
