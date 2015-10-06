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
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
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
public class ViewerNodeData implements GridDataViewer<GvData> {
    private Context mContext;
    private ReviewNode mNode;
    private ReviewsRepository mRepository;
    private GvList mCache;

    public ViewerNodeData(Context context, ReviewNode node, ReviewsRepository repository) {
        mContext = context;
        mNode = node;
        mRepository = repository;
    }

    @Override
    public GvList getGridData() {
        GvList data = makeGridData();
        mCache = data;
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
    public ReviewViewAdapter expandGridCell(GvData datum) {
        ReviewViewAdapter adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                adapter = expandReviews();
            } else {
                adapter = expandData((GvDataCollection) datum);
            }
        }

        return adapter;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
    }

    @Override
    public void setData(GvDataCollection<GvData> data) {

    }

    protected Context getContext() {
        return mContext;
    }

    protected ReviewNode getReviewNode() {
        return mNode;
    }

    protected ReviewsRepository getRepository() {
        return mRepository;
    }

    protected GvList makeGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(mNode, mRepository.getTagsManager());

        data.add(tagCollector.collectTags());
        data.add(MdGvConverter.convert(mNode.getCriteria()));
        data.add(MdGvConverter.convert(mNode.getImages()));
        data.add(MdGvConverter.convert(mNode.getComments()));
        data.add(MdGvConverter.convert(mNode.getLocations()));
        data.add(MdGvConverter.convert(mNode.getFacts()));

        return data;
    }

    protected ReviewViewAdapter expandReviews() {
        return ReviewListScreen.newScreen(mContext, mNode.getReview(), mRepository).getAdapter();
    }

    protected ReviewViewAdapter expandData(GvDataCollection data) {
        return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mNode, data, mRepository);
    }
}
