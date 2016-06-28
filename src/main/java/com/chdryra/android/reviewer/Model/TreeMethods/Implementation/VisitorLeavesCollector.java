/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorLeavesCollector implements VisitorDataGetter<ReviewNode> {
    private IdableList<ReviewNode> mData;

    public VisitorLeavesCollector(ReviewId rootId) {
        mData = new IdableDataList<>(rootId);
    }

    @Override
    public IdableCollection<ReviewNode> getData() {
        return mData;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        if(node.getChildren().size() == 0 && node.isValidReference()) mData.add(node);
    }
}
