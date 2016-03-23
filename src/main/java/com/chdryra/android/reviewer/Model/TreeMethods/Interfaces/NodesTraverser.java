/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface NodesTraverser {
    void traverse();

    void addVisitor(VisitorReviewNode visitor);
}
