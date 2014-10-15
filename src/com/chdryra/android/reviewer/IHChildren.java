/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * Created by: Rizwan Choudrey
 * On: 15/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class IHChildren extends InputHandlerReviewData<GVReviewSubjectRating> {
    public static final String SUBJECT = "subject";
    public static final String RATING  = "rating";

    IHChildren() {
        super(GVReviewDataList.GVType.CHILDREN);
    }

    IHChildren(GVReviewDataList<GVReviewSubjectRating> data) {
        super(data);
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVReviewSubjectRating gvData, Bundle args) {
        args.putString(getPackingTag(currentNew, SUBJECT), gvData.getSubject());
        args.putFloat(getPackingTag(currentNew, RATING), gvData.getRating());
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVReviewSubjectRating gvData, Intent data) {
        data.putExtra(getPackingTag(currentNew, SUBJECT), gvData.getSubject());
        data.putExtra(getPackingTag(currentNew, RATING), gvData.getRating());
    }

    @Override
    GVReviewSubjectRating unpack(CurrentNewDatum currentNew, Bundle args) {
        String subject = args.getString(getPackingTag(currentNew, SUBJECT));
        float rating = args.getFloat(getPackingTag(currentNew, RATING));

        return new GVReviewSubjectRating(subject, rating);
    }

    @Override
    GVReviewSubjectRating unpack(CurrentNewDatum currentNew, Intent data) {
        String subject = (String) data.getSerializableExtra(getPackingTag(currentNew, SUBJECT));
        float rating = data.getFloatExtra(getPackingTag(currentNew, RATING), 0);

        return new GVReviewSubjectRating(subject, rating);
    }
}
