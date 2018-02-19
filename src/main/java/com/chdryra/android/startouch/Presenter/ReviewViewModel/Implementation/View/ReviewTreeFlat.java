/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeFlat extends ReviewTree {
    public ReviewTreeFlat(ReviewNode toFlatten, final FactoryReviews reviewsFactory) {
        super(toFlatten);
        toFlatten.getReviews().dereference(new DataReference.DereferenceCallback<IdableList<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<ReviewReference>> value) {
                if(value.hasValue()) {
                    setNode(reviewsFactory.createTree(value.getData(), getSubject().getSubject()));
                }
            }
        });
    }
}
