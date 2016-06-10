/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see AndroidAppInstance
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

    private boolean contains(ReviewId id) {
        for (GvReviewOverview review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Override
    public boolean add(GvReviewOverview overview) {
        return !contains(overview.getReviewId()) && super.add(overview);
    }

    @Override
    public boolean contains(Object object) {
        try {
            GvReviewOverview item = (GvReviewOverview) object;
            return contains(item.getReviewId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
