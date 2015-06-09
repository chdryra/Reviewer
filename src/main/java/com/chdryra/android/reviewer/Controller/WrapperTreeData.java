/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeDataGetter;
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
    private boolean        mUniqueData;
    private TreeDataGetter mGetter;

    public WrapperTreeData(ReviewNode node, boolean uniqueData) {
        mNode = node;
        mGetter = new TreeDataGetter(mNode);
        mUniqueData = uniqueData;
    }

    @Override
    public GvDataList getGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvDataCollection data = new GvDataCollection(id);
        TagCollector tagCollector = new TagCollector(mNode);
        SubReviewCollector subReviewCollector = new SubReviewCollector(mNode);

        WrapperChildList wrapper = new WrapperChildList(mNode);
        GvDataList reviews = wrapper.getGridData();
        if (reviews.size() > 0) {
            data.add(reviews);
            data.add(MdGvConverter.convertChildren(mNode));
        }

        data.add(tagCollector.collectTags());
        data.add(subReviewCollector.collectCriteria());
        data.add(MdGvConverter.convert(mGetter.getImages(mUniqueData)));
        data.add(MdGvConverter.convert(mGetter.getComments(mUniqueData)));
        data.add(MdGvConverter.convert(mGetter.getLocations(mUniqueData)));
        data.add(MdGvConverter.convert(mGetter.getFacts(mUniqueData)));

        return data;
    }
}
