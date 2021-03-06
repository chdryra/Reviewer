/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see AppInstanceAndroid
 */
public class GvNodeList extends GvDataListImpl<GvNode> {
    public GvNodeList() {
        super(GvNode.TYPE, null);
    }

    public GvNodeList(GvReviewId parentId) {
        super(GvNode.TYPE, parentId);
    }

    public GvNodeList(GvNodeList data) {
        super(data);
    }

    public void unbind() {
        for (GvNode reference : this) {
            reference.unbind();
        }
    }

    @Override
    public boolean contains(Object object) {
        try {
            GvNode item = (GvNode) object;
            return contains(item.getReviewId());
        } catch (ClassCastException e) {
            return false;
        }
    }

    private boolean contains(ReviewId id) {
        for (GvNode review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }
}
