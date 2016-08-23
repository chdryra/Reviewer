/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorFactory {
    interface ItemVisitor<T extends HasReviewId> {
        VisitorDataGetter<T> newVisitor();
    }

    interface ListVisitor<T extends HasReviewId, R extends ReviewItemReference<T>, L extends ReviewListReference<T, R>> {
        VisitorDataGetter<L> newVisitor();
    }
}
