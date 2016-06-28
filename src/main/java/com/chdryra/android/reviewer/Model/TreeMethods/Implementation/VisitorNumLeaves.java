/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorNumLeaves implements VisitorReviewNode {
    private int mNumberLeaves = 0;

    public int getNumberLeaves() {
        return mNumberLeaves;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        if(node.getChildren().size() == 0 && node.isValidReference()) mNumberLeaves++;
    }
}
