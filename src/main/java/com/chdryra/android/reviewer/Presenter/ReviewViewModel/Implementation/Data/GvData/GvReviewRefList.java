/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see AndroidAppInstance
 */
public class GvReviewRefList extends GvDataListImpl<GvReviewRef> {
    public GvReviewRefList() {
        super(GvReviewRef.TYPE, null);
    }

    public GvReviewRefList(GvReviewId parentId) {
        super(GvReviewRef.TYPE, parentId);
    }

    public GvReviewRefList(GvReviewRefList data) {
        super(data);
    }

    public void unbind() {
        for(GvReviewRef reference : this) {
            reference.unbind();
        }
    }

    private boolean contains(ReviewId id) {
        for (GvReviewRef review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Override
    public boolean add(GvReviewRef overview) {
        return !contains(overview.getReviewId()) && super.add(overview);
    }

    @Override
    public boolean contains(Object object) {
        try {
            GvReviewRef item = (GvReviewRef) object;
            return contains(item.getReviewId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
