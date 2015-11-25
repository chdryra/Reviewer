/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;

/**
 * Used for Review summaries in published feed
 *
 * @see ApplicationInstance
 */
public class GvReviewOverviewList extends GvDataListImpl<GvReviewOverview> {
    public static final Parcelable.Creator<GvReviewOverviewList> CREATOR = new Parcelable
            .Creator<GvReviewOverviewList>() {
        @Override
        public GvReviewOverviewList createFromParcel(Parcel in) {
            return new GvReviewOverviewList(in);
        }

        @Override
        public GvReviewOverviewList[] newArray(int size) {
            return new GvReviewOverviewList[size];
        }
    };
    //Constructors
    public GvReviewOverviewList() {
        super(GvReviewOverview.TYPE, null);
    }

    public GvReviewOverviewList(Parcel in) {
        super(in);
    }

    public GvReviewOverviewList(GvReviewId parentId) {
        super(GvReviewOverview.TYPE, parentId);
    }

    public GvReviewOverviewList(GvReviewOverviewList data) {
        super(data);
    }

    private boolean contains(String id) {
        for (GvReviewOverview review : this) {
            if (review.getId().equals(id)) return true;
        }

        return false;
    }

    //Overridden
    public void add(GvReviewOverview overview) {
        if (!contains(overview.getId())) super.add(overview);
    }

    @Override
    public boolean contains(GvReviewOverview item) {
        return contains(item.getId());
    }

}
