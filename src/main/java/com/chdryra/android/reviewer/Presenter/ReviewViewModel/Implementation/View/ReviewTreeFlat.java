/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeFlat extends ReviewTree {
    public ReviewTreeFlat(ReviewNodeComponent initial, final ReviewNode toFlatten,
                          final FactoryReviews factory) {
        super(initial, factory.getNodeFactory().getBinderFactory());
        toFlatten.getData(new ReviewsCallback() {
            @Override
            public void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message) {
                if(!message.isError()) setNode(factory.createMetaTree(reviews, toFlatten.getSubject().getSubject()));
            }
        });
    }
}
