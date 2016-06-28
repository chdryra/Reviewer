/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Interfaces;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface TreeTraverser {
    interface TraversalCallback {
        void onTraversed(Map<String, VisitorReviewNode> visitors);
    }

    void traverse(TraversalCallback callback);

    void addVisitor(String id, VisitorReviewNode visitor);
}
