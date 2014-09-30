/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;

class ControllerReviewNodeCollection {
    private final RCollectionReviewNode                 mReviewNodes;
    private final HashMap<String, ControllerReviewNode> mControllers;

    public ControllerReviewNodeCollection(RCollectionReviewNode reviewNodes) {
        mReviewNodes = reviewNodes;
        mControllers = new HashMap<String, ControllerReviewNode>();
    }

    public int size() {
        return get().size();
    }

    RCollectionReviewNode get() {
        return mReviewNodes;
    }

    public GVReviewSubjectRatingList getGridViewableData() {
        GVReviewSubjectRatingList data = new GVReviewSubjectRatingList();
        for (Review r : get()) {
            data.add(r.getSubject().get(), r.getRating().get());
        }

        return data;
    }

    public GVReviewOverviewList getGridViewablePublished() {
        GVReviewOverviewList data = new GVReviewOverviewList();
        for (Review r : get()) {
            if (r.isPublished()) {
                ControllerReviewNode c = getControllerFor(r.getId().toString());
                GVImageList images = (GVImageList) c.getData(GVType.IMAGES);
                Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
                GVCommentList comments = (GVCommentList) c.getData(GVType.COMMENTS);
                String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline()
                        : null;
                GVLocationList locations = (GVLocationList) c.getData(GVType.LOCATIONS);
                String location = locations.size() > 0 ? locations.getItem(0).getName() : null;
                data.add(c.getId(), c.getSubject(), c.getRating(), cover, headline, location,
                        c.getAuthor(), c.getPublishDate());
            }
        }

        return data;
    }

    //***Accessesors***
    ControllerReviewNode getControllerFor(String id) {
        if (mControllers.get(id) == null) {
            mControllers.put(id, new ControllerReviewNode(get(id)));
        }

        return mControllers.get(id);
    }

    ReviewNode get(String id) {
        ReviewNode r = get().get(Controller.convertID(id));
        if (r == null) {
            r = FactoryReview.createNullReviewNode();
        }

        return r;
    }
}