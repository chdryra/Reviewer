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

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvReviewList;

import java.util.Date;

/**
 * {@link ReviewViewAdapter} for {@link ReviewIdableList} data.
 */
public class ReviewFeedAdapter extends ReviewViewAdapterBasic {
    private final String mTitle;
    private final ReviewIdableList<ReviewNode> mNodes;
    private Context mContext;

    public ReviewFeedAdapter(Context context, String authorName, ReviewIdableList<ReviewNode>
            nodes) {
        mContext = context;
        mTitle = authorName + "'s feed";
        mNodes = nodes;
    }

    @Override
    public String getSubject() {
        return mTitle;
    }

    @Override
    public float getRating() {
        return getAverageRating();
    }

    @Override
    public float getAverageRating() {
        return createReview().getRating().get();
    }

    @Override
    public GvDataList getGridData() {
        GvReviewList data = new GvReviewList();
        for (Review review : mNodes) {
            GvImageList images = MdGvConverter.convert(review.getImages());
            GvCommentList headlines = MdGvConverter.convert(review.getComments()).getHeadlines();
            GvLocationList locations = MdGvConverter.convert(review.getLocations());

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                    null;
            String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

            data.add(review.getId().toString(), review.getAuthor(),
                    review.getPublishDate(),
                    review.getSubject().get(), review.getRating().get(), cover, headline, location);
        }

        return data;
    }

    @Override
    public GvImageList getCovers() {
        return null;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
        return mNodes.containsId(ReviewId.fromString(overview.getId()));
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
        return mNodes.containsId(id) ? new ReviewNodeAdapter(mContext, mNodes.get(id)) : null;
    }

    private ReviewNode createReview() {
        return FactoryReview.createReviewCollection(Author.NULL_AUTHOR, new Date(), mTitle, mNodes);
    }
}
