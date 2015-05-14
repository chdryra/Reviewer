/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.NodeDataCollector;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperTreeData implements GridDataWrapper {
    private ReviewNode mNode;
    private NodeDataCollector mCollector;

    public WrapperTreeData(ReviewNode node) {
        mNode = node;
        mCollector = new NodeDataCollector(node);
    }

    @Override
    public GvDataList getGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvDataCollection data = new GvDataCollection(id);
        TagCollector tagCollector = new TagCollector(mNode);
        CriteriaCollector criteriaCollector = new CriteriaCollector(mNode);
        data.add(tagCollector.collectTags());
        data.add(criteriaCollector.collectCriteria());
        data.add(MdGvConverter.convert(mCollector.collectImages(true)));
        data.add(MdGvConverter.convert(mCollector.collectComments(true)));
        data.add(MdGvConverter.convert(mCollector.collectLocations(true)));
        data.add(MdGvConverter.convert(mCollector.collectFacts(true)));

        return data;
    }
}
