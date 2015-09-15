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
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
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
    private GvList mCache;
    private boolean mIsAggregate = false;

    public ViewerTreeData(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
    }

    @Override
    public GvList getGridData() {
        return mNode.getChildren().size() > 0 ? getAggregateGridData() : getNodeGridData();
    }

    private GvList getAggregateGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);

        TagCollector tagCollector = new TagCollector(mNode);
        ViewerChildList wrapper = new ViewerChildList(mContext, mNode);

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

        mCache = data;
        mIsAggregate = true;
        return data;
    }

    private GvList getNodeGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(mNode);

        data.add(tagCollector.collectTags());
        data.add(MdGvConverter.convert(mNode.getCriteria()));
        data.add(MdGvConverter.convert(mNode.getImages()));
        data.add(MdGvConverter.convert(mNode.getComments()));
        data.add(MdGvConverter.convert(mNode.getLocations()));
        data.add(MdGvConverter.convert(mNode.getFacts()));

        mCache = data;
        mIsAggregate = false;
        return data;
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
    public ReviewViewAdapter<? extends GvData> expandItem(GvData datum) {
        if (isExpandable(datum)) {
            ReviewViewAdapter<? extends GvData> parent
                    = ReviewListScreen.newScreen(mContext, mNode).getAdapter();
            if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                return parent;
            }

            GvDataCollection<? extends GvData> data = (GvDataCollection<? extends GvData>) datum;
            ReviewViewAdapter<? extends GvData> adapter;
            if (mIsAggregate) {
                adapter = FactoryReviewViewAdapter.newExpandToReviewsAdapter(mContext, data,
                        parent.getSubject());
            } else {
                adapter = FactoryReviewViewAdapter.newExpandToDataAdapter(parent, data);
            }
            return adapter;
        }

        return null;
    }
}
