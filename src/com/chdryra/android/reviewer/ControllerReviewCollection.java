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
 * Similar to ControllerReview but for RCollectionReview data.
 *
 * @see ControllerReview
 * @see RCollectionReview
 */
class ControllerReviewCollection<T extends Review> {
    protected RCollectionReview<T>              mReviews;
    protected HashMap<String, ControllerReview> mControllers;

    ControllerReviewCollection(RCollectionReview<T> reviews) {
        reinitialise(reviews);
    }

    protected void reinitialise(RCollectionReview<T> reviews) {
        mReviews = reviews;
        mControllers = new HashMap<String, ControllerReview>();
    }

    int size() {
        return mReviews.size();
    }

    GVReviewSubjectRatingList getGridViewableData() {
        GVReviewSubjectRatingList data = new GVReviewSubjectRatingList();
        for (Review r : mReviews) {
            data.add(r.getSubject().get(), r.getRating().get());
        }

        return data;
    }

    GVReviewOverviewList getGridViewablePublished() {
        GVReviewOverviewList data = new GVReviewOverviewList();
        for (Review r : mReviews) {
            if (r.isPublished()) {
                ControllerReview c = getControllerFor(r.getId().toString());

                GVImageList images = (GVImageList) c.getData(GVReviewDataList.GVType.IMAGES);
                GVCommentList comments = (GVCommentList) c.getData(GVReviewDataList.GVType.COMMENTS);
                GVLocationList locations = (GVLocationList) c.getData(GVReviewDataList.GVType
                        .LOCATIONS);

                Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
                String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline()
                        : null;
                String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

                data.add(c.getId(), c.getSubject(), c.getRating(), cover, headline, location,
                        c.getAuthor(), c.getPublishDate());
            }
        }

        return data;
    }

    private Review get(String id) {
        return mReviews.get(RDId.generateId(id));
    }

    private ControllerReview getControllerFor(String id) {
        if (mControllers.get(id) == null) {
            mControllers.put(id, new ControllerReview(get(id)));
        }

        return mControllers.get(id);
    }
}
