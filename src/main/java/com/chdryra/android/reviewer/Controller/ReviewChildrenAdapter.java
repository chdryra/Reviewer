/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Controller;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.content.Context;
import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvReviewList;

import java.util.ArrayList;

/**
 * {@link ReviewViewAdapter} for {@link ReviewIdableList} data.
 */
public class ReviewChildrenAdapter extends ReviewViewAdapterBasic {
    private ReviewNode mNode;
    private Context mContext;

    public ReviewChildrenAdapter(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().get();
    }

    @Override
    public float getRating() {
        return mNode.getRating().get();
    }

    @Override
    public float getAverageRating() {
        if (mNode.isRatingAverageOfChildren()) return getRating();
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        visitor.visit(mNode);
        return visitor.getRating();
    }

    @Override
    public GvDataList getGridData() {
        GvReviewList data = new GvReviewList();
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

            data.add(review.getId().toString(), review.getAuthor(), review.getPublishDate(),
                    review.getSubject().get(), review.getRating().get(), cover, headline,
                    locationNames);
        }

        return data;
    }

    @Override
    public GvImageList getCovers() {
        return new GvImageList();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
        return mNode.getChildren().containsId(ReviewId.fromString(overview.getId()));
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
            return expandReview(ReviewId.fromString(overview.getId()));
        } else {
            return null;
        }
    }

    public ReviewViewAdapter expandReview(ReviewId id) {
        ReviewIdableList<ReviewNode> nodes = mNode.getChildren();
        return nodes.containsId(id) ? new ReviewChildrenAdapter(mContext, nodes.get(id)) : null;
    }

    protected Context getContext() {
        return mContext;
    }
}
