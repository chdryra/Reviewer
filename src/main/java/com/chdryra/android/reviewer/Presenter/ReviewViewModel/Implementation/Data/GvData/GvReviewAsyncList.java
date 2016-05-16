/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see ApplicationInstance
 */
public class GvReviewAsyncList extends GvDataListImpl<GvReviewAsync> {
    public static final Creator<GvReviewAsyncList> CREATOR = new Creator<GvReviewAsyncList>() {
        @Override
        public GvReviewAsyncList createFromParcel(Parcel in) {
            return new GvReviewAsyncList(in);
        }

        @Override
        public GvReviewAsyncList[] newArray(int size) {
            return new GvReviewAsyncList[size];
        }
    };

    //Constructors
    public GvReviewAsyncList() {
        super(GvReviewAsync.TYPE, null);
    }

    public GvReviewAsyncList(Parcel in) {
        super(in);
    }

    public GvReviewAsyncList(GvReviewId parentId) {
        super(GvReviewAsync.TYPE, parentId);
    }

    public GvReviewAsyncList(GvReviewAsyncList data) {
        super(data);
    }

    private boolean contains(ReviewId id) {
        for (GvReviewAsync review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Override
    public boolean add(GvReviewAsync overview) {
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
