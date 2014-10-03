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
public class ControllerReviewCollection<T extends Review> {
    protected RCollectionReview<T>              mReviews;
    protected HashMap<String, ControllerReview> mControllers;

    public ControllerReviewCollection(RCollectionReview<T> reviews) {
        init(reviews);
    }

    protected void init(RCollectionReview<T> reviews) {
        mReviews = reviews;
        mControllers = new HashMap<String, ControllerReview>();
    }

    public int size() {
        return mReviews.size();
    }

    public GVReviewSubjectRatingList getGridViewableData() {
        GVReviewSubjectRatingList data = new GVReviewSubjectRatingList();
        for (Review r : mReviews) {
            data.add(r.getSubject().get(), r.getRating().get());
        }

        return data;
    }

    public GVReviewOverviewList getGridViewablePublished() {
        GVReviewOverviewList data = new GVReviewOverviewList();
        for (Review r : mReviews) {
            if (r.isPublished()) {
                ControllerReview c = getControllerFor(r.getId().toString());
                GVImageList images = (GVImageList) c.getData(GVReviewDataList.GVType.IMAGES);
                Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
                GVCommentList comments = (GVCommentList) c.getData(GVReviewDataList.GVType.COMMENTS);
                String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline()
                        : null;
                GVLocationList locations = (GVLocationList) c.getData(GVReviewDataList.GVType.LOCATIONS);
                String location = locations.size() > 0 ? locations.getItem(0).getName() : null;
                data.add(c.getId(), c.getSubject(), c.getRating(), cover, headline, location,
                        c.getAuthor(), c.getPublishDate());
            }
        }

        return data;
    }

    //***Accessesors***
    ControllerReview getControllerFor(String id) {
        if (mControllers.get(id) == null) {
            T review = get(id);
            if(review == null) {
                return new ControllerReview(FactoryReview.createNullReview());
            } else {
                mControllers.put(id, new ControllerReview(review));
            }
        }

        return mControllers.get(id);
    }

    private T get(String id) {
        return mReviews.get(Controller.convertID(id));
    }
}
