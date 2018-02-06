/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataBucketer;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorFactory {
    interface ItemVisitor<T extends HasReviewId> {
        VisitorDataGetter<T> newVisitor();
    }

    interface ListVisitor<L extends ReviewListReference<?, ?>> {
        VisitorDataGetter<L> newVisitor();
    }

    interface BucketVisitor<BucketingValue, Data extends HasReviewId> {
        VisitorDataBucketer<BucketingValue, Data> newVisitor();
    }
}
