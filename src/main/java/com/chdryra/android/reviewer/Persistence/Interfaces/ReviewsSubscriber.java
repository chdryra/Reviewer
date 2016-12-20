/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsSubscriber {
    String getSubscriberId();

    void onReviewAdded(ReviewReference reference);

    void onReviewEdited(ReviewReference reference);

    void onReviewRemoved(ReviewReference reference);

    void onReferenceInvalidated(ReviewId reviewId);
}
