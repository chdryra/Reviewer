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

/**
 * Expands on {@link ControllerReview} to include setters on {@link ReviewEditable}.
 */
class ControllerReviewEditable extends ControllerReview<ReviewEditable> {
    ControllerReviewEditable(ReviewEditable review) {
        super(review);
    }

    void setSubject(String subject) {
        getControlledReview().setSubject(subject);
    }

    void setRating(float rating) {
        getControlledReview().setRating(rating);
    }

    void setData(GVReviewDataList data) {
        GVReviewDataList.GVType dataType = data.getGVType();
        ReviewEditable r = getControlledReview();
        if (dataType == GVReviewDataList.GVType.COMMENTS) {
            r.setComments(RdGvConverter.convert((GVCommentList) data, r));
        } else if (dataType == GVReviewDataList.GVType.IMAGES) {
            r.setImages(RdGvConverter.convert((GVImageList) data, r));
        } else if (dataType == GVReviewDataList.GVType.FACTS) {
            r.setFacts(RdGvConverter.convert((GVFactList) data, r));
        } else if (dataType == GVReviewDataList.GVType.URLS) {
            r.setURLs(RdGvConverter.convert((GVUrlList) data, r));
        } else if (dataType == GVReviewDataList.GVType.LOCATIONS) {
            r.setLocations(RdGvConverter.convert((GVLocationList) data, r));
        } else if (dataType == GVReviewDataList.GVType.TAGS) {
            setTags((GVTagList) data);
        }
    }
}
