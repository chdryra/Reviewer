/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Similar to {@link ReviewAdapter} but for {@link RCollectionReview} data.
 */
public class ControllerReviewCollection<T extends Review> {
    private RCollectionReview<T>       mReviews;
    private HashMap<String, GvAdapter> mAdapters;

    public ControllerReviewCollection(RCollectionReview<T> reviews) {
        init(reviews);
    }

    public void addReview(T review) {
        mReviews.add(review);
    }

    public GvDataList toGridViewable(boolean publishedOnly) {
        return publishedOnly ? toGridViewablePublished() : toGridViewableAll();
    }

    protected void init(RCollectionReview<T> reviews) {
        mReviews = reviews;
        mAdapters = new HashMap<>();
    }

    private GvDataList toGridViewableAll() {
        GvChildrenList data = new GvChildrenList();
        for (Review r : mReviews) {
            data.add(new GvChildrenList.GvChildReview(r.getSubject().get(), r.getRating().get()));
        }

        return data;
    }

    private GvDataList toGridViewablePublished() {
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

    private T get(String id) {
        return mReviews.get(ReviewId.generateId(id));
    }

    private GvAdapter getAdapterFor(String id) {
        if (mAdapters.get(id) == null) {
            mAdapters.put(id, new ReviewAdapter<>(get(id)));
        }

        return mAdapters.get(id);
    }
}
