/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.TreeDataGetter;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is a summary of how many of each type of {@link com.chdryra.android.reviewer.Model
 * .MdData}. {@link TagsManager.ReviewTag} is in the tree.
 * Includes number of reviews and subjects if a meta-review.
 */
public class ViewerTreeData implements GridDataViewer<GvData> {
    private Context mContext;
    private ReviewNode mNode;
    private TreeDataGetter mGetter;

    public ViewerTreeData(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
        mGetter = new TreeDataGetter(mNode);
    }

    @Override
    public GvList getGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(mNode);

        ViewerChildList wrapper = new ViewerChildList(mNode, null);
        GvReviewOverviewList reviews = wrapper.getGridData();
        if (reviews.size() > 0) {
            data.add(reviews);
            data.add(Aggregater.aggregate(MdGvConverter.convertChildAuthors(mNode)));
            data.add(Aggregater.aggregate(MdGvConverter.convertChildSubjects(mNode)));
            data.add(Aggregater.aggregate(MdGvConverter.convertChildPublishDates(mNode)));
        }

        data.add(Aggregater.aggregate(tagCollector.collectTags()));
        data.add(Aggregater.aggregate(collectCriteria()));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getImages())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getComments())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getLocations())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getFacts())));

        return data;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if (!datum.hasElements()) return false;

        GvDataCollection data = (GvDataCollection) datum;
        GvList gridData = getGridData();
        for (GvData list : gridData) {
            ((GvDataCollection) list).sort();
        }
        data.sort();

        return gridData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            ReviewViewAdapter parent = FactoryReviewViewAdapter.newChildOverviewAdapter(mContext,
                    mNode);
            if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                return parent;
            }

            return FactoryReviewViewAdapter.newGvDataCollectionAdapter(mContext, parent,
                    (GvDataMap) datum);
        }

        return null;
    }

    private GvChildReviewList collectCriteria() {
        GvChildReviewList criteria = new GvChildReviewList(GvReviewId.getId(mNode.getId()
                .toString()));
        criteria.addList(MdGvConverter.convertChildren(mNode.expand()));
        for (ReviewNode node : mNode.getChildren()) {
            criteria.addList(MdGvConverter.convertChildren(node.expand()));
        }

        return criteria;
    }
}
