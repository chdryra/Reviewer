/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeComponent extends ReviewNode {

    boolean addChild(ReviewNodeComponent childNode);

    void addChildren(Iterable<ReviewNodeComponent> children);

    void removeChild(ReviewId reviewId);

    void setParent(@Nullable ReviewNodeComponent parent);
}
