/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Used for review children (sub-reviews).
 */
public class GvCriterionList extends GvDataListImpl<GvCriterion> {
    public static final Parcelable.Creator<GvCriterionList> CREATOR = new Parcelable
            .Creator<GvCriterionList>() {
        @Override
        public GvCriterionList createFromParcel(Parcel in) {
            return new GvCriterionList(in);
        }

        @Override
        public GvCriterionList[] newArray(int size) {
            return new GvCriterionList[size];
        }
    };
    
    //Constructors
    public GvCriterionList() {
        super(GvCriterion.TYPE, null);
    }

    public GvCriterionList(Parcel in) {
        super(in);
    }

    public GvCriterionList(GvReviewId id) {
        super(GvCriterion.TYPE, id);
    }

    public GvCriterionList(GvCriterionList data) {
        super(data);
    }

    //public methods
    public float getAverageRating() {
        float rating = 0;
        for (GvCriterion review : this) {
            rating += review.getRating() / size();
        }

        return rating;
    }

    public boolean contains(String subject) {
        for (GvCriterion review : this) {
            if (review.getSubject().equals(subject)) return true;
        }

        return false;
    }

//Classes

}
