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
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

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
    private TagsManager mTagsManager;
    private GvList mCache;
    private boolean mIsAggregate = false;

    public ViewerTreeData(Context context, ReviewNode node, TagsManager tagsManager) {
        mContext = context;
        mNode = node;
        mTagsManager = tagsManager;
        mIsAggregate = mNode.getChildren().size() > 1;
    }

    @Override
    public GvList getGridData() {
        IdableList<ReviewNode> children = mNode.getChildren();
        int numChildren = children.size();
        if(numChildren > 1) return getAggregateGridData();

        return getNodeGridData(children.size() == 0 ? mNode : children.getItem(0));
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if (!datum.hasElements() || mCache == null) return false;

        GvDataCollection data = (GvDataCollection) datum;
        for (GvData list : mCache) {
            ((GvDataCollection) list).sort();
        }
        data.sort();

        return mCache.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        if (isExpandable(datum)) {
            ReviewViewAdapter parent = getReviewListAdapter();
            if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                return parent;
            }

            GvDataCollection data = (GvDataCollection) datum;
            ReviewViewAdapter adapter;
            if (mIsAggregate) {
                String subject = data.getStringSummary();
                adapter = FactoryReviewViewAdapter.newExpandToReviewsAdapterForCanonical(mContext,
                        (GvCanonicalCollection<? extends GvData>) data, subject);
            } else {
                adapter = FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, parent, data);
            }
            return adapter;
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
    }

    @Override
    public void setData(GvDataCollection<GvData> data) {

    }

    private ReviewViewAdapter getReviewListAdapter() {
        if(mIsAggregate) {
            return ReviewListScreen.newScreen(mContext, mNode, mTagsManager).getAdapter();
        } else {
            return ReviewListScreen.newScreen(mContext, mNode.getReview(), mTagsManager).getAdapter();
        }
    }

    private GvList getAggregateGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);

        TagCollector tagCollector = new TagCollector(mNode, mTagsManager);
        ViewerChildList wrapper = new ViewerChildList(mContext, mNode, mTagsManager);

        data.add(wrapper.getGridData());
        data.add(Aggregater.aggregate(MdGvConverter.convertChildAuthors(mNode)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildSubjects(mNode)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildPublishDates(mNode)));

        data.add(Aggregater.aggregate(tagCollector.collectTags()));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mNode.getCriteria())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mNode.getImages())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mNode.getComments())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mNode.getLocations())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mNode.getFacts())));

        mIsAggregate = true;
        mCache = data;
        return data;
    }

    private GvList getNodeGridData(ReviewNode node) {
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(node, mTagsManager);

        data.add(tagCollector.collectTags());
        data.add(MdGvConverter.convert(node.getCriteria()));
        data.add(MdGvConverter.convert(node.getImages()));
        data.add(MdGvConverter.convert(node.getComments()));
        data.add(MdGvConverter.convert(node.getLocations()));
        data.add(MdGvConverter.convert(node.getFacts()));

        mIsAggregate = false;
        mCache = data;
        return data;
    }
}
