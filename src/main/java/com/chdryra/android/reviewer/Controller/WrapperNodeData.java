/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperNodeData implements GridDataWrapper {
    private ReviewNode mNode;

    public WrapperNodeData(ReviewNode node) {
        mNode = node;
    }

    @Override
    public GvDataList getGridData() {
        ReviewId id = mNode.getId();

        GvDataCollection data = new GvDataCollection(GvReviewId.getId(id.toString()));
        data.add(MdGvConverter.getTags(id.toString()));
        data.add(MdGvConverter.convertChildren(mNode));
        data.add(MdGvConverter.convert(mNode.getImages()));
        data.add(MdGvConverter.convert(mNode.getComments()));
        data.add(MdGvConverter.convert(mNode.getLocations()));
        data.add(MdGvConverter.convert(mNode.getFacts()));

        return data;
    }
}

