/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewOverviewList}.
 */
public class ViewerChildList implements GridDataViewer<GvReviewOverviewList.GvReviewOverview> {
    private ReviewNode mNode;
    private GridCellExpander<GvReviewOverviewList.GvReviewOverview> mExpander;

    public ViewerChildList(ReviewNode node, GridCellExpander<GvReviewOverviewList.GvReviewOverview>
            expander) {
        mNode = node;
        mExpander = expander;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    @Override
    public GvReviewOverviewList getGridData() {
        GvReviewOverviewList data = new GvReviewOverviewList(GvReviewId.getId(mNode.getId()
                .toString()));
        for (Review review : mNode.getChildren()) {
            GvImageList images = MdGvConverter.convert(review.getImages());
            GvCommentList headlines = MdGvConverter.convert(review.getComments()).getHeadlines();
            GvLocationList locations = MdGvConverter.convert(review.getLocations());

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                    null;

            ArrayList<String> locationNames = new ArrayList<>();
            for (GvLocationList.GvLocation location : locations) {
                locationNames.add(location.getShortenedName());
            }

            data.add(review.getId().toString(), review.getAuthor(), review.getPublishDate()
                            .getDate(),
                    review.getSubject().get(), review.getRating().get(), cover, headline,
                    locationNames);
        }

        return data;
    }

    @Override
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        return mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvReviewOverviewList.GvReviewOverview datum) {
        return mExpander.expandItem(datum);
    }
}
