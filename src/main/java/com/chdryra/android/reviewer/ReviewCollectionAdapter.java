/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Similar to {@link ReviewAdapter} but for {@link RCollectionReview} data.
 */
public class ReviewCollectionAdapter<T extends Review> {
    private RCollectionReview<T>       mReviews;
    private HashMap<String, GvAdapter> mAdapters;

    public ReviewCollectionAdapter() {
        mReviews = new RCollectionReview<>();
        mAdapters = new HashMap<>();
    }

    public void add(T review) {
        mReviews.add(review);
    }

    public GvDataList toGridViewable() {
        GvReviewList data = new GvReviewList();
        for (Review r : mReviews) {
            GvAdapter adapter = getAdapterFor(r.getId().toString());

            GvImageList images = (GvImageList) adapter.getData(GvDataList.GvType.IMAGES);
            GvCommentList comments = (GvCommentList) adapter.getData(GvDataList.GvType.COMMENTS);
            GvLocationList locations = (GvLocationList) adapter.getData(GvDataList.GvType
                    .LOCATIONS);

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline() : null;
            String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

            data.add(adapter.getId(), adapter.getAuthor().getName(), adapter.getPublishDate(),
                    adapter.getSubject(),
                    adapter.getRating(),
                    cover, headline, location);
        }

        return data;
    }

    private GvAdapter getAdapterFor(String id) {
        if (mAdapters.get(id) == null) {
            mAdapters.put(id, new ReviewAdapter<>(mReviews.get(ReviewId.generateId(id))));
        }

        return mAdapters.get(id);
    }
}
