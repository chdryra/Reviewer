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
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

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
public class ViewerReviewData implements GridDataViewer<GvData> {
    private Context mContext;
    private ReviewNode mNode;
    private ReviewsRepository mRepository;
    private GvList mCache;

    public ViewerReviewData(Context context, ReviewNode node, ReviewsRepository repository) {
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
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mNode,
                    (GvDataCollection<? extends GvData>) datum, mRepository);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
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
        Review review = mNode.getReview();
        GvReviewId id = GvReviewId.getId(review.getId().toString());

        GvList data = new GvList(id);
        data.add(MdGvConverter.getTags(review.getId().toString(), mRepository.getTagsManager()));
        data.add(MdGvConverter.convert(review.getCriteria()));
        data.add(MdGvConverter.convert(review.getImages()));
        data.add(MdGvConverter.convert(review.getComments()));
        data.add(MdGvConverter.convert(review.getLocations()));
        data.add(MdGvConverter.convert(review.getFacts()));

        return data;
    }
}
