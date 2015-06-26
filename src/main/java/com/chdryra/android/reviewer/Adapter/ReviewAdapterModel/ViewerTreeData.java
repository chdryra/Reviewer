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
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.TagCollector;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;

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
    private TreeDataAggregator mGetter;

    public ViewerTreeData(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
        mGetter = new TreeDataAggregator(mNode);
    }

    @Override
    public GvList getGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(mNode);

        ViewerChildList wrapper = new ViewerChildList(mNode, null);
        GvReviewList reviews = wrapper.getGridData();
        if (reviews.size() > 0) {
            data.add(reviews);
            data.add(MdGvConverter.convertChildAuthors(mNode));
            data.add(MdGvConverter.convertChildSubjects(mNode));
            data.add(MdGvConverter.convertChildPublishDates(mNode));
        }

        data.add(tagCollector.collectTags());
        data.add(collectCriteria());
        data.add(MdGvConverter.convert(mGetter.getImages()));
        data.add(MdGvConverter.convert(mGetter.getComments()));
        data.add(MdGvConverter.convert(mGetter.getLocations()));
        data.add(MdGvConverter.convert(mGetter.getFacts()));

        return data;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if(!datum.isList()) return false;

        GvDataList data = (GvDataList)datum;
        GvList gridData = getGridData();
        for (GvData list : gridData) {
            if(list.isList()) ((GvDataList)list).sort();
        }
        data.sort();

        return gridData.contains(datum) && datum.hasElements();
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            ReviewViewAdapter parent = FactoryReviewViewAdapter.newChildOverviewAdapter(mContext,
                    mNode);
            if (datum.getGvDataType() == GvReviewList.GvReviewOverview.TYPE) {
                return parent;
            }

            return FactoryReviewViewAdapter.newGvDataListAdapter(mContext, parent, (GvDataList)
                    datum);
        }

        return null;
    }

    private GvChildList collectCriteria() {
        GvChildList criteria = new GvChildList(GvReviewId.getId(mNode.getId().toString()));
        criteria.addList(MdGvConverter.convertChildren(mNode.expand()));
        for (ReviewNode node : mNode.getChildren()) {
            criteria.addList(MdGvConverter.convertChildren(node.expand()));
        }

        return criteria;
    }
}
