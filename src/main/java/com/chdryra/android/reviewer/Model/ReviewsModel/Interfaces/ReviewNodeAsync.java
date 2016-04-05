/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeAsync<T extends ReviewNode> extends ReviewNode, ReviewNode.NodeObserver {
    void updateNode(T node);
}