/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeFlat extends ReviewTree
        implements DataReference.DereferenceCallback<IdableList<ReviewReference>> {

    private FactoryReviews mReviewsFactory;

    public ReviewTreeFlat(ReviewNode toFlatten, FactoryReviews reviewsFactory) {
        super(toFlatten);
        mReviewsFactory = reviewsFactory;
        toFlatten.getReviews().dereference(this);
    }

    @Override
    public void onDereferenced(@Nullable IdableList<ReviewReference> data, CallbackMessage message) {
        if(!message.isError() && data != null) {
            setNode(mReviewsFactory.createTree(data, getSubject().getSubject()));
        }
    }
}
