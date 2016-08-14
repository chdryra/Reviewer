/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeSourceCallback extends ReviewTree
        implements ReviewsSource.ReviewsSourceCallback {

    public ReviewTreeSourceCallback(ReviewNodeComponent initial) {
        super(initial);
    }

    @Override
    public void onMetaReviewCallback(RepositoryResult result) {
        ReviewNodeComponent node = result.getReviewTree();
        if (!result.isError() && node != null) {
            setNode(node);
        }
    }
}
