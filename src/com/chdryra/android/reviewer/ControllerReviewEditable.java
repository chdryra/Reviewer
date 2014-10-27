/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolderData;

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

    //Title
    void setSubject(String subject) {
        getControlledReview().setSubject(subject);
    }

    //Rating
    void setRating(float rating) {
        getControlledReview().setRating(rating);
    }

    //Other data
    <T extends GVReviewDataList<? extends ViewHolderData>> void setData(T data) {
        GVReviewDataList.GVType dataType = data.getGVType();
        if (dataType == GVReviewDataList.GVType.COMMENTS) {
            setComments((GVCommentList) data);
        } else if (dataType == GVReviewDataList.GVType.IMAGES) {
            setImages((GVImageList) data);
        } else if (dataType == GVReviewDataList.GVType.FACTS) {
            setFacts((GVFactList) data);
        } else if (dataType == GVReviewDataList.GVType.URLS) {
            setUrls((GVUrlList) data);
        } else if (dataType == GVReviewDataList.GVType.LOCATIONS) {
            setLocations((GVLocationList) data);
        } else if (dataType == GVReviewDataList.GVType.TAGS) {
            setTags((GVTagList) data);
        }
    }

    private void setComments(GVCommentList comments) {
        ReviewEditable r = getControlledReview();
        RDList<RDComment> rdComments = new RDList<RDComment>();
        for (GVCommentList.GVComment comment : comments) {
            rdComments.add(new RDComment(comment.getComment(), r));
        }

        r.setComments(rdComments);
    }

    private void setImages(GVImageList images) {
        ReviewEditable r = getControlledReview();
        RDList<RDImage> rdImages = new RDList<RDImage>();
        for (GVImageList.GVImage image : images) {
            rdImages.add(new RDImage(image.getBitmap(), image.getLatLng(), image.getCaption(),
                                     image.isCover(), r));
        }

        r.setImages(rdImages);
    }

    private void setFacts(GVFactList gvFacts) {
        ReviewEditable r = getControlledReview();
        RDList<RDFact> facts = new RDList<RDFact>(r);
        for (GVFactList.GVFact fact : gvFacts) {
            facts.add(new RDFact(fact.getLabel(), fact.getValue(), r));
        }

        r.setFacts(facts);
    }

    private void setUrls(GVUrlList urlList) {
        if (urlList.size() == 0) {
            return;
        }

        ReviewEditable r = getControlledReview();
        RDList<RDUrl> rdUrls = new RDList<RDUrl>();
        for (GVUrlList.GVUrl url : urlList) {
            rdUrls.add(new RDUrl(url.getUrl(), r));
        }

        r.setURLs(rdUrls);
    }

    private void setLocations(GVLocationList locations) {
        ReviewEditable r = getControlledReview();
        RDList<RDLocation> rdLocations = new RDList<RDLocation>();
        for (GVLocationList.GVLocation location : locations) {
            rdLocations.add(new RDLocation(location.getLatLng(), location.getName(), r));
        }

        r.setLocations(rdLocations);
    }
}
