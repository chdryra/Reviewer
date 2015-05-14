/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.NodeDataCollector;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewData extends AdapterReviewNode {
    private NodeDataCollector mCollector;

    public AdapterReviewData(Context context, ReviewNode node) {
        super(node);
        mCollector = new NodeDataCollector(node);
        setExpander(new ExpanderGridCell(context, this));
    }

    @Override
    public String getSubject() {
        return String.valueOf(mCollector.collectSubjects(true).size()) + " subjects";
    }

    @Override
    public GvDataList getGridData() {
        ReviewNode node = getNode();

        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvDataCollection data = new GvDataCollection(id);
        TagCollector tagCollector = new TagCollector(node);
        CriteriaCollector criteriaCollector = new CriteriaCollector(node);
        data.add(tagCollector.collectTags());
        data.add(criteriaCollector.collectCriteria());
        data.add(MdGvConverter.convert(mCollector.collectImages(true)));
        data.add(MdGvConverter.convert(mCollector.collectComments(true)));
        data.add(MdGvConverter.convert(mCollector.collectLocations(true)));
        data.add(MdGvConverter.convert(mCollector.collectFacts(true)));
        setWrapper(new WrapperGvDataList(data));

        return super.getGridData();
    }
}
