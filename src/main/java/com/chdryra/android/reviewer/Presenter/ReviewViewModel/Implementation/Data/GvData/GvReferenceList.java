/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see AndroidAppInstance
 */
public class GvReferenceList extends GvDataListImpl<GvReference> {
    public static final Creator<GvReferenceList> CREATOR = new Creator<GvReferenceList>() {
        @Override
        public GvReferenceList createFromParcel(Parcel in) {
            return new GvReferenceList(in);
        }

        @Override
        public GvReferenceList[] newArray(int size) {
            return new GvReferenceList[size];
        }
    };

    //Constructors
    public GvReferenceList() {
        super(GvReference.TYPE, null);
    }

    public GvReferenceList(Parcel in) {
        super(in);
    }

    public GvReferenceList(GvReviewId parentId) {
        super(GvReference.TYPE, parentId);
    }

    public GvReferenceList(GvReferenceList data) {
        super(data);
    }

    public void unbind() {
        for(GvReference reference : this) {
            reference.unbind();
        }
    }

    private boolean contains(ReviewId id) {
        for (GvReference review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Override
    public boolean add(GvReference overview) {
        return !contains(overview.getReviewId()) && super.add(overview);
    }

    @Override
    public boolean contains(Object object) {
        try {
            GvReference item = (GvReference) object;
            return contains(item.getReviewId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
